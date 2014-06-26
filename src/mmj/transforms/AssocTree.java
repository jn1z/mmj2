package mmj.transforms;

import mmj.lang.ParseNode;
import mmj.lang.Stmt;

public class AssocTree {
    final int size;
    final AssocTree[] subTrees;

    AssocTree() {
        size = 1;
        subTrees = null;
    }

    AssocTree(final AssocTree left, final AssocTree right) {
        subTrees = new AssocTree[]{left, right};
        size = left.size + right.size;
    }

    AssocTree(final AssocTree[] subTrees) {
        this.subTrees = subTrees;
        assert subTrees.length == 2;
        size = subTrees[0].size + subTrees[1].size;
    }

    AssocTree(final int from, final AssocTree left, final AssocTree right) {
        assert from == 0 || from == 1;
        subTrees = from == 0 ? new AssocTree[]{left, right}
            : new AssocTree[]{right, left};
        size = left.size + right.size;
    }

    AssocTree duplicate() {
        if (subTrees != null)
            return new AssocTree(subTrees[0].duplicate(),
                subTrees[1].duplicate());
        else
            return new AssocTree();
    }

    @Override
    public String toString() {
        String res = "";
        res += size;

        if (subTrees != null)
            res += "[" + subTrees[0].toString() + ","
                + subTrees[1].toString() + "]";

        return res;
    }

    public static AssocTree createAssocTree(final ParseNode curNode,
        final GeneralizedStmt assocProp, final WorksheetInfo info)
    {
        final Stmt stmt = curNode.getStmt();
        final ParseNode[] childrent = curNode.getChild();
    
        final Stmt assocStmt = assocProp.stmt;
    
        if (stmt != assocStmt)
            return new AssocTree();
    
        final AssocTree[] subTrees = new AssocTree[2];
    
        for (int i = 0; i < 2; i++) {
            final ParseNode child = childrent[assocProp.varIndexes[i]];
            if (AssociativeInfo.isAssociativeWithProp(child, assocProp, info))
                subTrees[i] = createAssocTree(child, assocProp, info);
            else
                subTrees[i] = new AssocTree();
        }
    
        return new AssocTree(subTrees);
    }
}