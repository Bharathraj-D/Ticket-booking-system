import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Linkedlist obj = new Linkedlist();

        while (true) {
            System.out.println("What action do you need to perform: ");
            System.out.println("1.List the tickets.");
            System.out.println("2.Book new ticket");
            System.out.println("3.Find the seat of the Ticket");
            System.out.println("4.Move the specific ticket to the front seat.");
            System.out.println("5.Cancel the firstly booked ticket.");
            System.out.println("6.Cancel the specific ticket.");
            System.out.println("7.Exit");
            System.out.println("Select your option: ");

            int n = sc.nextInt();

            switch (n) {
                case 1:
                    obj.print();
                    break;

                case 2:
                    System.out.print("Enter the ticket id: ");
                    int id = sc.nextInt();
                    System.out.print("Enter the Customer name : ");
                    String name = sc.next();
                    System.out.print("Enter the issue date and time : ");
                    String details = sc.next();
                    System.out.print("Enter the priority of ticket(Normal,High,Critical): ");
                    String priority = sc.next().toLowerCase();
                    obj.add(id, name, details, priority);
                    break;

                case 3:
                    System.out.print("Enter the ticket id: ");
                    id = sc.nextInt();
                    obj.findTicket(id);
                    break;

                case 4:
                    System.out.print("Enter the ticket id: ");
                    id = sc.nextInt();
                    obj.escalateTicket(id);
                    break;

                case 5:
                    System.out.print("Enter the priority: ");
                    String gg = sc.next().toLowerCase();
                    obj.resolveTicket(gg);
                    break;

                case 6:
                    System.out.print("Enter the Ticket Id to delete: ");
                    int key = sc.nextInt();
                    obj.delete(key);
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Wrong selection !!!!");
            }
        }
    }
}

class Node {
    int ticket_id;
    String customer_name;
    String issue_description;
    String priority;
    Node next;

    Node(int ticket_id, String customer_name, String issue_description, String priority) {
        this.ticket_id = ticket_id;
        this.customer_name = customer_name;
        this.issue_description = issue_description;
        this.priority = priority;
        this.next = null;
    }
}

class Linkedlist {
    Node Nhead, Hhead, Chead;

    public void add(int ticket_id, String customer_name, String issue_description, String priority) {
        Node newNode = new Node(ticket_id, customer_name, issue_description, priority);

        if (priority.equals("critical")) {
            Chead = insertEnd(Chead, newNode);
        } else if (priority.equals("high")) {
            Hhead = insertEnd(Hhead, newNode);
        } else if (priority.equals("normal")) {
            Nhead = insertEnd(Nhead, newNode);
        }
    }

    private Node insertEnd(Node head, Node newNode) {
        if (head == null) return newNode;
        Node cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = newNode;
        return head;
    }

    public void delete(int ticket_id) {
        Chead = deleteFromList(Chead, ticket_id);
        Hhead = deleteFromList(Hhead, ticket_id);
        Nhead = deleteFromList(Nhead, ticket_id);
    }

    private Node deleteFromList(Node head, int ticket_id) {
        if (head == null) return null;
        if (head.ticket_id == ticket_id) return head.next;

        Node cur = head;
        while (cur.next != null) {
            if (cur.next.ticket_id == ticket_id) {
                cur.next = cur.next.next;
                break;
            }
            cur = cur.next;
        }
        return head;
    }

    public void print() {
        printList("Critical", Chead);
        printList("High", Hhead);
        printList("Normal", Nhead);
    }

    private void printList(String name, Node head) {
        System.out.println("==== " + name + " ====");
        Node cur = head;
        while (cur != null) {
            System.out.println(cur.ticket_id + " " + cur.customer_name + " " + cur.issue_description);
            cur = cur.next;
        }
    }

    public void resolveTicket(String priority) {
        if (priority.equals("critical") && Chead != null)
            Chead = Chead.next;
        else if (priority.equals("high") && Hhead != null)
            Hhead = Hhead.next;
        else if (priority.equals("normal") && Nhead != null)
            Nhead = Nhead.next;
    }

    public void escalateTicket(int ticket_id) {
        Chead = moveToFront(Chead, ticket_id);
        Hhead = moveToFront(Hhead, ticket_id);
        Nhead = moveToFront(Nhead, ticket_id);
    }

    private Node moveToFront(Node head, int ticket_id) {
        if (head == null || head.ticket_id == ticket_id) return head;

        Node cur = head;
        while (cur.next != null) {
            if (cur.next.ticket_id == ticket_id) {
                Node temp = cur.next;
                cur.next = temp.next;
                temp.next = head;
                return temp;
            }
            cur = cur.next;
        }
        return head;
    }

    public void findTicket(int ticket_id) {
        findInList("Critical", Chead, ticket_id);
        findInList("High", Hhead, ticket_id);
        findInList("Normal", Nhead, ticket_id);
    }

    private void findInList(String name, Node head, int ticket_id) {
        int pos = 1;
        Node cur = head;
        while (cur != null) {
            if (cur.ticket_id == ticket_id) {
                System.out.println(name + " : " + pos);
                return;
            }
            cur = cur.next;
            pos++;
        }
    }
}
