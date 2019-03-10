//implementing push, peek and pop
public class myStack {

    Integer Size;
    Integer top;
    Integer arr[];

    public myStack(int n){
//        Stack max size
        Size = n;
//        an empty stack as array
        arr = new Integer[Size];
//        initializing top
        top = 0;
    }

//  Check if the stack is empty
    public boolean empty(){
        if (top == 0){
            return true;
        }else{
            return false;
        }
    }

//    add an element to the current top and increase top by 1

    public void push(Integer num){
//         we add to the top if there is space in the stack max size
        if (top<Size) {
            arr[top] = num;
            top++;
        }else{
            System.out.println("Stack Overflow!");
        }
    }

//    Popping the element at top
    public Integer Pop(){
        if (!this.empty()){
            int temp = this.Peek();
            arr[top-1] = 0;
            top--;
            return temp;
        }else{
            return null;
        }

    }
//        Accessing last element put inside the stack

    public Integer Peek(){
        if (top > 0) {
            return arr[top - 1];
        }
        else{
            return null;
        }
    }

}
