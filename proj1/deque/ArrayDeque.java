package deque;

class ArrayDeque<Item> implements Deque<Item>{
    Item[] array;
    int first;
    int last;
    public int length (){
        return this.array.length;
    }
    // create an empty array with default size 8
    public ArrayDeque(){
        this.array = (Item[]) new Object[8];
        this.first =4;
        this.last =4;
    }
    //create an empty array with size n
    public ArrayDeque(int n){
        if (n<0){
            throw new IndexOutOfBoundsException();
        }
        else if (n<8){
            this.array = (Item[]) new Object[8];
            int first =4;
            int last =4;
        }
        else{
            this.array = (Item[]) new Object[n];
            int first = n / 2;
            int last = n / 2;
        }

    }

    /** Two helper functions for add and remove that resizes array.
     *  The decrease size function helps to keep the usage of our memory.
     */
    private void increaseSize(){
        Item[] newArray = (Item[]) new Object[2*this.array.length];
        System.arraycopy(this.array,this.first+1,newArray,this.array.length/2,this.size());
        int size = this.size();
        this.first = this.array.length/2-1;
        this.last = this.first + size+1;
        this.array = newArray;
    }
    private void decreaseSize(){
        Item[] newArray = (Item[]) new Object[2*this.size()];
        System.arraycopy(this.array,this.first+1,newArray,this.size()/2,this.size());
        int size = this.size();
        this.first = size/2-1;
        this.last = this.first + size+1;
        this.array = newArray;
    }

    //add and remove functions
    public void addFirst(Item item) {
        if(this.isEmpty()){
            this.array[first] = item;
            this.first --;
            this.last++;
        }
        else if(this.first-1 <0 ){
            this.increaseSize();
            this.array[first] = item;
            this.first --;
        }
        else{
            this.array[first]= item;
            this.first--;
        }
    }

    public void addLast(Item item){
        if(this.isEmpty() ){
            this.array[last] = item;
            this.first --;
            this.last++;
        }
        else if(this.last +1 >= this.array.length){
            this.increaseSize();
            this.array[last] = item;
            this.last ++;
        }
        else{
            this.array[last]= item;
            this.last++;
        }
    }

    @Override
    public Item removeLast() {
        if (this.isEmpty()){
            return null;
        }
        else if((float) (this.size()-1)/this.array.length <= 0.25){
            this.decreaseSize();
            Item result = this.array[last-1];
            this.array[last-1] = null;
            this.last --;
            return result;
        }
        else{
            Item result = this.array[last-1];
            this.array[last-1] = null;
            this.last--;
            return result;
        }

    }

    @Override
    public Item removeFirst() {
        if (this.isEmpty()){
            return null;
        }
        else if((float) (this.size()-1)/this.array.length <= 0.25){
            this.decreaseSize();
            Item result = this.array[first+1];
            this.array[first+1] = null;
            this.first++;
            return result;
        }
        else{
            Item result = this.array[first+1];
            this.array[first+1] = null;
            this.first++;
            return result;
        }
    }

    public int size(){
        return this.last - this.first -1;
    }
    public Item get(int index){

        if(this.isEmpty() || index >=this.size()){
            return null;
        }
        return this.array[this.first+1+index];
    }

    @Override
    public Iterable<Item> iterator() {
        return null;
    }

    public boolean isEmpty(){
        return this.size()<0;
    }

    @Override
    public void printDeque() {
        if (this.isEmpty()){
            System.out.println();

        }
        else{
            for(int i = this.first+1; i<this.last;i++){
                System.out.print(this.array[i] + " ");
            }
            System.out.println();
        }
    }
}