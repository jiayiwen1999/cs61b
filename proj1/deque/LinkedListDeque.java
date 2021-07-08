package deque;

import java.sql.Array;


class LinkedListDeque<Item> implements Deque<Item>{
    private static class Node<Item>{
        public Item item;
        public Node<Item> next;
        public Node<Item> prev;
        public Node (Item item, Node<Item> prev, Node<Item> next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    int size;
    Node<Item> sentinel;
    /**
     * constructor
     */

    //empty list constructor
    public LinkedListDeque(){
        sentinel = new Node<>(null,null,null);
        sentinel.next = sentinel.prev;
        this.size =0;
    }

    public LinkedListDeque(Item item){
        sentinel = new Node<>(null,null,null);
        sentinel.next = new Node<>(item,sentinel,sentinel);
        sentinel.prev = sentinel.next;
        this.size =1;
    }



    public int size(){
        return this.size;
    }


    public void addFirst(Item item) {
        if (this.size == 0 ){
            this.sentinel.next = new Node<>(item,this.sentinel,this.sentinel);
            this.sentinel.prev = this.sentinel.next;
            this.size = 1;
        }
        else {
            this.sentinel.next.prev = new Node<>(item, this.sentinel, this.sentinel.next);
            this.sentinel.next = this.sentinel.next.prev;
            this.size += 1;
        }
    }


    public void addLast(Item item) {
        if (size ==0){
            this.sentinel.prev = new Node<>(item,this.sentinel,this.sentinel);
            this.sentinel.next = this.sentinel.prev;
            this.size = 1;
        }
        else {
            this.sentinel.prev = new Node<>(item, this.sentinel.prev, this.sentinel);
            this.sentinel.prev.prev.next = this.sentinel.prev;
            this.size += 1;
        }
    }



    public boolean isEmpty() {
        return this.size == 0;
    }


    public Item removeFirst() {
        if (size ==0) return null;
        Item first = this.sentinel.next.item;
        this.sentinel.next= this.sentinel.next.next;
        this.sentinel.next.prev = this.sentinel;
        this.size -=1;
        return first;
    }


    public Item removeLast() {
        if(size ==0) return null;
        Item last = this.sentinel.prev.item;
        this.sentinel.prev = this.sentinel.prev.prev;
        this.sentinel.prev.next = this.sentinel;
        this.size -=1;
        return last;
    }


    public Item get(int index) {
        LinkedListDeque<Item> curr= this;
        for (int i =0;i<index;i++){
            if (curr.sentinel.next == null){
                return null;
            }
            curr.sentinel.next = curr.sentinel.next.next;
        }
        return curr.sentinel.next.item;
    }


    public void printDeque() {
        StringBuilder result= new StringBuilder();
        LinkedListDeque<Item> curr = this;
        for(int i =0;i<size;i++){
            result.append(curr.sentinel.next.item).append(" ");
            curr.sentinel.next = curr.sentinel.next.next;
        }
        System.out.println(result);
        System.out.println();
    }


    public Iterable<Item> iterator() {
        //TODO:need to complete
        return null;
    }


    public boolean equals(Object object){
        if (!(object instanceof LinkedListDeque )) return false;
        else if (this.size != ((LinkedListDeque<?>) object).size) return false;
        else{
            if (this.size ==1) {
                return this.sentinel.next.item.equals(((LinkedListDeque<?>) object).sentinel.next.item);
            }
            else {
                LinkedListDeque<Item> head = this;
                LinkedListDeque<Item> ob = (LinkedListDeque<Item>) object;
                if (this.sentinel.next.item.equals(((LinkedListDeque<?>) object).sentinel.next.item)){
                    head.sentinel.next = head.sentinel.next.next;
                    ob.sentinel.next = ob.sentinel.next.next;
                    return head.equals(ob);
                }
                return false;
            }
        }

    }

    public Item getRecursive(int index){
        LinkedListDeque<Item> head = this;
        if (index ==0 ){
            return head.sentinel.next.item;
        }
        head.sentinel.next = head.sentinel.next.next;
        return head.getRecursive(index-1);
    }

}