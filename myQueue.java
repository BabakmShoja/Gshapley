public class myQueue {// I constructed the queue from node then linked list then queue because I was not sure if we can use Linked list
    static class Node {
        // Holding data and next reference
        int data;
        Node next;

        // initializing the value of data and next
        public Node(int key) {
            data = key;
            next = null;
        }

        // Displaying data
        public void dispNode() {
            System.out.println(data);
        }
    }

    static class LinkedList {
        // Node class reference
        Node first;

        // assign null to first reference
        public LinkedList() {
            first = null;
        }

        // INSERT
        public void insert(int i) {
            Node newNode = new Node(i);

            //When Linked List is empty first is newNode
            if (first == null) {
                first = newNode;
                return;
            }

            // when Linked List is not empty or assigned newNode to first above and we go to the end
            Node tempNode = first;
            while (tempNode.next != null) {
                tempNode = tempNode.next;
            }
            // When reaching the last node newNode is assigned
            tempNode.next = newNode;
        }

        //REMOVE (simply going to the next node)
        public Node remove() {
            Node temp = first;
            first = first.next;
            return temp;
        }

        //DISPLAY for displaying the value
        public void display() {
            Node tempNode = first;
            while (tempNode != null) {
                tempNode.dispNode();
                tempNode = tempNode.next;
            }
            System.out.println();
        }
    }



    static class Queue {
        LinkedList myLL = new LinkedList();

        //INSERT
        public void insert(int i) {
            myLL.insert(i);
        }

        //Pop
        public int Pop() {
            int popit = myLL.first.data;
            myLL.remove();
            return popit;
        }

        //Remove (I did not use this for the main code
        public void remove() {
            myLL.remove();
        }

        //DISPLAY
        public void display() {
            myLL.display();
        }

    }


}