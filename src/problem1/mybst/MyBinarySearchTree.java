package problem1.mybst;

import problem1.node.TreeNode;
public class MyBinarySearchTree<E> implements BinarySearchTree<E> {
    private TreeNode<E> root = null;
    private int size = 0;
    private int notContainLeftChild = 0;

    public int getSize() {
        return size;
    }

    public TreeNode<E> getRoot() {
        return root;
    }


    private TreeNode<E> addInFamily(E data, TreeNode<E> currentMember) {
        if (currentMember == null) {
            return new TreeNode<>(data);
        } else if ((Integer) data < (Integer) currentMember.getData()) {
            currentMember.setLeftChild(addInFamily(data, currentMember.getLeftChild()));
        } else {
            currentMember.setRightChild(addInFamily(data, currentMember.getRightChild()));
        }
        return currentMember;
    }

//    public int parentNoContainLeftChild() {
//
//    }


    @Override
    public boolean add(E data) {
        root = addInFamily(data, root);
        size++;
        return true;
    }


    private TreeNode<E> deleteFromFamily(E data, TreeNode<E> currentMember) {
        if (currentMember == null) {
            System.out.println("there are 0 family member ");
            return null;
        }
        if (data.equals(currentMember.getData())) {
            //case 1 : no children of the node to be delete
            if (currentMember.getLeftChild() == null && currentMember.getRightChild() == null) {
                return null;
            }
            // case 2 : only one child of the node to be deleted
            if (currentMember.getRightChild() == null) {
                return currentMember.getLeftChild();
            }
            if (currentMember.getLeftChild() == null) {
                return currentMember.getRightChild();
            }
            // case 3 : two children of the node to be deleted
            E smallestValue = findSmallest(currentMember.getRightChild());
            currentMember.setData(smallestValue);
            currentMember.setRightChild(deleteFromFamily(smallestValue, currentMember.getRightChild()));
            return currentMember;
        }
        if ((Integer) currentMember.getData() < (Integer) data) {
            deleteFromFamily(data, currentMember.getRightChild());
        } else {
            currentMember.setRightChild(deleteFromFamily(data, currentMember.getRightChild()));
        }
        return currentMember;
    }


    private E findSmallest(TreeNode<E> findPOutMember) {
        return (findPOutMember.getLeftChild() == null) ? findPOutMember.getData() : findSmallest(findPOutMember.getLeftChild());
    }

    @Override
    public boolean remove(E data) {
        root = deleteFromFamily(data, root);
        size--;
        return true;
    }


    private boolean searchFromFamily(E data, TreeNode<E> currentMember) {
        boolean result = false;
        if (currentMember == null) {
            System.out.println("There zero member in family ");
        }
        if ((Integer) currentMember.getData() == (Integer) data) {
            result = true;
        } else if ((Integer) currentMember.getData() < (Integer) data) {
            searchFromFamily(data, currentMember.getRightChild());
        } else {
            searchFromFamily(data, currentMember.getLeftChild());
        }
        return result;
    }


    @Override
    public boolean search(E data) {
        return searchFromFamily(data, root);
    }

    private void printOnlyLeftChildren(TreeNode<E> currentNode) {
        if (currentNode == null) {
            System.out.println("there are no other left member in Family");
        } else {
            System.out.println(currentNode.getData());
            printOnlyLeftChildren(currentNode.getLeftChild());
        }
    }

    @Override
    public boolean traverse() {
        printOnlyLeftChildren(root);
        return true;
    }

    public void traverseViaOrder(int data) {
        switch (data) {
            case 1 -> traversePerOrder(root);
            case 2 -> traversePostOrder(root);
        }
    }

    public void traversePostOrder(TreeNode<E> currentNode) {
        if (currentNode != null) {
            traversePostOrder(currentNode.getLeftChild());
            traversePostOrder(currentNode.getRightChild());
            visit(currentNode.getData());
        }
    }

    public void traversePerOrder(TreeNode<E> currentNode) {
        if (currentNode == null) {
            visit(currentNode.getData());
            traversePerOrder(currentNode.getLeftChild());
            traversePerOrder(currentNode.getRightChild());
        }
    }

    private void visit(E data) {
        System.out.println(" " + data);
    }

    public TreeNode preOrderSuccessor(TreeNode<E> node) {
        if (node.getLeftChild() != null) {
            return node.getLeftChild();
        }
        TreeNode temp = node.getRightChild();
        if (temp == null) {
            TreeNode currParent = node.getParentNode();
            while (currParent.getRightChild() == null) {
                currParent = currParent.getParentNode();
            }
            return currParent.getRightChild();
        }
        return temp;
    }

}