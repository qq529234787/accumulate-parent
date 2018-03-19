package com.wme.learn.linked;

/**
 * @Title: 判断两个单链表是否交叉，并找到交叉点
 *  方案一：链表如果相交则两个链表的形态应该是Y或者是V型
 *      比较两个链表的尾部是否相同，如果相同则两个链表相交，交叉点可以通过两个链表的长度差进行计算，因为交点后面的长度是相同的，
 *      差值是交点前形成的，可以分别设定两个指针，长的链表先遍历到差值的位置，短的链表指向头部，分别遍历直到相等就是交点的所在
 *   方案二：如果只判断是否相交而无需返回交叉点的情况，也可以用方案二。
 *      如果两个链表相交，将一个链表的首尾相连，那么两个链表合在一起就会形成一个有环的链表，问题就归结到单链表是否有环的问题
 * @Auther: ming
 * @Date: 2018/1/24
 * @Version: 1.0
 */
public class LinkNode {

    Object item;
    LinkNode next;

    public LinkNode(Object item){
        this.item = item;
    }

    /**
     * 方案一：比较两个链表的尾部是否相同，如果相同则两个链表相交，交叉点可以通过两个链表的长度差进行计算，因为交点后面的长度是相同的，
     *      差值是交点前形成的，可以分别设定两个指针，长的链表先遍历到差值的位置，短的链表指向头部，分别遍历直到相等就是交点的所在
     * @param lindA
     * @param lindB
     * @return
     */
    public static LinkNode returnCrossNode(LinkNode lindA, LinkNode lindB){
        if(lindA == null || lindB == null){
            return null;
        }

        int lengthA = 1;
        int lengthB = 1;
        LinkNode tailA = lindA;
        LinkNode tailB = lindB;
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
            LinkNode longLink = lengthA>lengthB ? lindA : lindB;
            LinkNode shortLink = lengthA<lengthB ? lindA : lindB;

            for(int i=0; i<len; i++) {
                longLink = longLink.next;
            }
            while (shortLink != null && longLink != null && shortLink != longLink){
                longLink = longLink.next;
                shortLink = shortLink.next;
            }
            return shortLink;
        }

        return null;
    }

    /**
     * 判断一个链表是否有环
     *
     *  如果一个链表有环, 那么它肯定只有一个环.(一个相交结点)
     *  算法：设两个指针p,q, 初始化指向头. p以步长2的速度向前跑, q的步长是1.这样, 如果链表不存在环, p和q肯定不会相遇.
     *        如果存在环, p和q一定会相遇.(就像两个速度不同的汽车在一个环上跑绝对会相遇).复杂度O(n)
     */
    public boolean isCross(LinkNode head){
        if(head == null || head.next == null){
            return false;
        }

        LinkNode p = head, q = head;
        while (q != null && q.next != null) {
            q = q.next.next;
            p = p.next;

            if(p == q){
                return true;
            }
        }
        return  false;
    }

    /**
     * 反转单链表1: 从原链表的头部一个一个取节点并插入到新链表的头部
     * @param head
     */
    public static LinkNode reverseLink1(LinkNode head){
       LinkNode newNode = null;
       for(LinkNode p = head; p != null; ){
           LinkNode temp = p.next;
           p.next = newNode;

           newNode = p;
           p = temp;
       }
        return newNode;
    }

    /**
     * 打印连表的所有节点的值
     * @param head
     */
    public static void printNodeItem(LinkNode head){
        StringBuffer sb = new StringBuffer();
        while (head != null) {
            sb.append(head.item);
            sb.append("|");
            head = head.next;
        }
        System.out.print(sb.toString());
    }

    /**
     * 测试连表反转
     * @param args
     */
    public static void main(String[] args) {
        LinkNode head = null;
        for(int i=0; i<10; i++){
            if(head == null){
                head = new LinkNode(i);
            }else {
                LinkNode node = new LinkNode(i);
                LinkNode pre = head;
                while (pre.next != null) {
                    pre = pre.next;
                }
                pre.next = node;
            }
        }
        printNodeItem(head);
        head = reverseLink1(head);
        System.out.println("----------reverseLink---------");
        printNodeItem(head);

    }

}
