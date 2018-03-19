package com.wme.learn.linked;

/**
 * @Title: 连表结构练习
 * @Auther: ming
 * @Date: 2018/1/24
 * @Version: 1.0
 */
public class LinkObject {
    // 头节点
    private Node head;
    // 连表长度
    private int length;

    /**
     * 向链表中插入数据(尾部插入)
     */
    public void addNode(Object item){
        Node newNode = new Node(item); // 实例化一个节点
        if(head == null){
            head = newNode;
            length++;
            return;
        }

        Node tempNode = head;
        while(tempNode.next != null){
            tempNode = tempNode.next;
        }
        tempNode.next = newNode;
        length++;
    }

    /**
     * @return 返回节点长度
     */
    public int length(){
        return length;
    }

    /**
     * 打印连表元素
     */
    public void printList() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.item);
            tmp = tmp.next;
        }
    }

    /**
     * 返回两条单列表的交叉节点
     * @param lindA
     * @param lindB
     * @return
     */
    public Node returnCrossNode(LinkObject lindA, LinkObject lindB){
        if(lindA == null || lindB == null){
            return null;
        }
        if(lindA.head == null || lindB.head == null){
            return null;
        }

        int lengthA = 1;
        int lengthB = 1;
        Node tailA = lindA.head;
        Node tailB = lindB.head;
        while (tailA.next != null) {
            tailA = tailA.next;
            lengthA++;
        }
        while (tailB.next != null) {
            tailB = tailB.next;
            lengthB++;
        }

        // 尾部相同,说明肯定有交叉点
        if(tailA == tailB){
            int len = lengthA>lengthB ? lengthA-lengthB : lengthB-lengthA;
            LinkObject longLink = lengthA>lengthB ? lindA : lindB;
            LinkObject shortLink = lengthA<lengthB ? lindA : lindB;

            Node longNode = longLink.head;
            for(int i=0; i<len; i++) {
                longNode = longNode.next;
            }
            Node shortNode = shortLink.head;
            while (shortNode != null && longLink != null && shortNode != longNode){
                longNode = longNode.next;
                shortNode = shortNode.next;
            }
            return shortNode;
        }

        return null;
    }

    /**
     * 连表节点（内部类）
     */
    class Node{
        Object item;
        Node next;

        public Node(Object item){
            this.item = item;
        }
    }

}

