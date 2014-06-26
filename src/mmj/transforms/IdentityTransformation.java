package mmj.transforms;

import mmj.lang.ParseNode;
import mmj.pa.ProofStepStmt;

/** No transformation at all =) */
class IdentityTransformation extends Transformation {

    public IdentityTransformation(final DataBaseInfo dbInfo,
        final ParseNode originalNode)
    {
        super(dbInfo, originalNode);
    }

    @Override
    public ParseNode getCanonicalNode(final WorksheetInfo info) {
        return originalNode;
    }

    @Override
    public ProofStepStmt transformMeToTarget(final Transformation target,
        final WorksheetInfo info)
    {
        assert target.originalNode.isDeepDup(originalNode);
        return null; // nothing to do
    }
}