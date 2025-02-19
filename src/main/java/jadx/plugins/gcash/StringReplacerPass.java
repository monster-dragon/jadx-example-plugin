package jadx.plugins.gcash;

import java.io.IOException;

import jadx.core.dex.nodes.ClassNode;
import jadx.core.dex.nodes.InsnNode;
import jadx.core.dex.nodes.MethodNode;
import jadx.core.dex.nodes.RootNode;
import jadx.core.dex.instructions.ConstStringNode;
import jadx.core.dex.instructions.InvokeNode;
import jadx.core.dex.instructions.InvokeType;
import jadx.core.dex.instructions.args.InsnArg;
import jadx.core.dex.instructions.args.LiteralArg;
import jadx.api.data.CommentStyle;
import jadx.api.plugins.pass.JadxPassInfo;
import jadx.api.plugins.pass.impl.OrderedJadxPassInfo;
import jadx.api.plugins.pass.types.JadxDecompilePass;
import jadx.core.utils.exceptions.JadxRuntimeException;
import jadx.plugins.gcash.runtime.Strings.StringIndexer;


/**
 * A Jadx decompilation pass that replaces calls to {@code StringIndexer._getString()}
 * with the actual string values during the decompilation process.
 *
 * <p>This pass processes each method in the decompiled classes and looks for
 * invocations of the {@code StringIndexer._getString()} method. When such an
 * invocation is found, it replaces the call with the corresponding string value.
 *
 * <p>The pass also adds a comment to each class indicating that the strings
 * have been replaced by this plugin.
 *
 * @see JadxDecompilePass
 * @see StringIndexer
 */
public class StringReplacerPass implements JadxDecompilePass {
	private String comment;

	/**
	 * Provides information about this decompilation pass.
	 * This method is called by the Jadx framework to get pass metadata.
	 *
	 * @return a {@link JadxPassInfo} object containing the pass metadata
	 */
	@Override
	public JadxPassInfo getInfo() {
		return new OrderedJadxPassInfo(
				"StringReplacePass",
				"Replace StringIndexer._getString calls with actual strings")
				.before("RegionMakerVisitor");
	}

	/**
	 * Initializes the pass with the given root node.
	 * This method is called by the Jadx framework during pass initialization.
	 *
	 * @param root the root node of the decompiled code
	 */
	@Override
	public void init(RootNode root) {
		this.comment = "String replaced by jadx plugin (" + JadxGcashPlugin.PLUGIN_ID + ")";
	}

	/**
	 * Visits a class node and adds a comment indicating that strings have been replaced.
	 * This method is called by the Jadx framework for each class node.
	 *
	 * @param cls the class node to visit
	 * @return false to indicate that the class node should not be visited further
	 */
	@Override
	public boolean visit(ClassNode cls) {
		cls.addCodeComment(comment, CommentStyle.BLOCK);
		return false;
	}

	/**
	 * Visits a method node and replaces calls to {@code StringIndexer._getString()}
	 * with the actual string values.
	 * This method is called by the Jadx framework for each method node.
	 *
	 * @param mth the method node to visit
	 */
	@Override
	public void visit(MethodNode mth) {
		for (InsnNode node : mth.getInstructions()) {
			if (node instanceof InvokeNode) {
				InvokeNode invokeNode = (InvokeNode) node;
				if (invokeNode.getInvokeType() == InvokeType.STATIC
						&& invokeNode
						.getCallMth()
						.getFullName()
						.equals("runtime.Strings.StringIndexer._getString(Ljava/lang/String;)Ljava/lang/String;")) {
					InsnArg arg = invokeNode.getArg(0);
					if (arg instanceof LiteralArg) {
						LiteralArg literalArg = (LiteralArg) arg;
						String strArg = Long.toString(literalArg.getLiteral());
						try {
							String replacement = StringIndexer._getString(strArg);
							node.replaceArg(arg, InsnArg.wrapArg(new ConstStringNode(replacement)));
						} catch (IOException e) {
							throw new JadxRuntimeException("Failed to replace string", e);
						}
					}
				}
			}
		}
	}
}
