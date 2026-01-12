import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class CircularLinkedList {
    Node head;
    Node tail;

    public void insert(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = tail = newNode;
            tail.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
    }

    public void delete(int key) {
        if (head == null) return;

        Node current = head;
        Node prev = tail;

        do {
            if (current.data == key) {
                if (current == head && current == tail) {
                    head = tail = null;
                } else if (current == head) {
                    head = head.next;
                    tail.next = head;
                } else if (current == tail) {
                    tail = prev;
                    tail.next = head;
                } else {
                    prev.next = current.next;
                }
                return;
            }
            prev = current;
            current = current.next;
        } while (current != head);
    }

    public void search(int key) {
        if (head == null) {
            System.out.println("List empty");
            return;
        }

        Node temp = head;
        int pos = 1;

        do {
            if (temp.data == key) {
                System.out.println("Found at position " + pos);
                return;
            }
            pos++;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Not found");
    }

    public void display() {
        if (head == null) {
            System.out.println("List empty");
            return;
        }

        Node temp = head;
        do {
            System.out.print(temp.data + " ");
            temp = temp.next;
        } while (temp != head);

        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CircularLinkedList cll = new CircularLinkedList();

        while (true) {
            System.out.println("1.Insert");
            System.out.println("2.Delete");
            System.out.println("3.Search");
            System.out.println("4.Display");
            System.out.println("5.Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    cll.insert(sc.nextInt());
                    break;

                case 2:
                    cll.delete(sc.nextInt());
                    break;

                case 3:
                    cll.search(sc.nextInt());
                    break;

                case 4:
                    cll.display();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
