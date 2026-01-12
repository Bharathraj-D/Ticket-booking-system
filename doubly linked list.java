import java.util.Scanner;

class Node {
    int ticket_id;
    String customer_name;
    String issue_description;
    String priority;
    Node next;
    Node previous;

    Node(int ticket_id, String customer_name, String issue_description, String priority) {
        this.ticket_id = ticket_id;
        this.customer_name = customer_name;
        this.issue_description = issue_description;
        this.priority = priority;
    }
}

class Linkedlist {

    Node Chead, Ctail;
    Node Hhead, Htail;
    Node Nhead, Ntail;

    public void add(int id, String name, String issue, String priority) {
        Node newNode = new Node(id, name, issue, priority);

        if (priority.equals("critical")) {
            if (Chead == null) {
                Chead = Ctail = newNode;
            } else {
                Ctail.next = newNode;
                newNode.previous = Ctail;
                Ctail = newNode;
            }
        } else if (priority.equals("high")) {
            if (Hhead == null) {
                Hhead = Htail = newNode;
            } else {
                Htail.next = newNode;
                newNode.previous = Htail;
                Htail = newNode;
            }
        } else if (priority.equals("normal")) {
            if (Nhead == null) {
                Nhead = Ntail = newNode;
            } else {
                Ntail.next = newNode;
                newNode.previous = Ntail;
                Ntail = newNode;
            }
        } else {
            System.out.println("Wrong priority");
        }
    }

    public void resolveTicket(String priority) {
        if (priority.equals("critical")) {
            removeFrontCritical();
        } else if (priority.equals("high")) {
            removeFrontHigh();
        } else if (priority.equals("normal")) {
            removeFrontNormal();
        } else {
            System.out.println("Wrong choice");
        }
    }

    private void removeFrontCritical() {
        if (Chead == null) return;
        if (Chead == Ctail) {
            Chead = Ctail = null;
        } else {
            Chead = Chead.next;
            Chead.previous = null;
        }
    }

    private void removeFrontHigh() {
        if (Hhead == null) return;
        if (Hhead == Htail) {
            Hhead = Htail = null;
        } else {
            Hhead = Hhead.next;
            Hhead.previous = null;
        }
    }

    private void removeFrontNormal() {
        if (Nhead == null) return;
        if (Nhead == Ntail) {
            Nhead = Ntail = null;
        } else {
            Nhead = Nhead.next;
            Nhead.previous = null;
        }
    }

    public void delete(int id) {
        if (deleteFromList(id, "critical")) return;
        if (deleteFromList(id, "high")) return;
        if (deleteFromList(id, "normal")) return;
        System.out.println("Ticket not found");
    }

    private boolean deleteFromList(int id, String priority) {
        Node head = getHead(priority);
        Node tail = getTail(priority);
        Node current = head;

        while (current != null) {
            if (current.ticket_id == id) {
                if (current == head && current == tail) {
                    setHeadTail(priority, null, null);
                } else if (current == head) {
                    head = head.next;
                    head.previous = null;
                    setHead(priority, head);
                } else if (current == tail) {
                    tail = tail.previous;
                    tail.next = null;
                    setTail(priority, tail);
                } else {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void escalateTicket(int id) {
        if (moveUp(id, "normal", "high")) return;
        if (moveUp(id, "high", "critical")) return;
        System.out.println("Ticket not found or already critical");
    }

    private boolean moveUp(int id, String from, String to) {
        Node current = getHead(from);

        while (current != null) {
            if (current.ticket_id == id) {
                delete(id);
                add(current.ticket_id, current.customer_name, current.issue_description, to);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void findTicket(int id) {
        int pos = search(Chead, id);
        if (pos != -1) {
            System.out.println("Critical : " + pos);
            return;
        }

        pos = search(Hhead, id);
        if (pos != -1) {
            System.out.println("High : " + pos);
            return;
        }

        pos = search(Nhead, id);
        if (pos != -1) {
            System.out.println("Normal : " + pos);
            return;
        }

        System.out.println("Ticket Not Found");
    }

    private int search(Node head, int id) {
        int pos = 1;
        Node current = head;
        while (current != null) {
            if (current.ticket_id == id) return pos;
            pos++;
            current = current.next;
        }
        return -1;
    }

    public void print() {
        if (Chead == null && Hhead == null && Nhead == null) {
            System.out.println("No tickets available");
            return;
        }

        System.out.println("====== CRITICAL ======");
        printList(Chead);

        System.out.println("====== HIGH ======");
        printList(Hhead);

        System.out.println("====== NORMAL ======");
        printList(Nhead);
    }

    private void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.println(current.ticket_id + " " + current.customer_name + " " + current.issue_description);
            current = current.next;
        }
    }

    private Node getHead(String priority) {
        if (priority.equals("critical")) return Chead;
        if (priority.equals("high")) return Hhead;
        return Nhead;
    }

    private Node getTail(String priority) {
        if (priority.equals("critical")) return Ctail;
        if (priority.equals("high")) return Htail;
        return Ntail;
    }

    private void setHead(String priority, Node node) {
        if (priority.equals("critical")) Chead = node;
        else if (priority.equals("high")) Hhead = node;
        else Nhead = node;
    }

    private void setTail(String priority, Node node) {
        if (priority.equals("critical")) Ctail = node;
        else if (priority.equals("high")) Htail = node;
        else Ntail = node;
    }

    private void setHeadTail(String priority, Node head, Node tail) {
        setHead(priority, head);
        setTail(priority, tail);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Linkedlist obj = new Linkedlist();

        while (true) {
            System.out.println("1.List tickets");
            System.out.println("2.Book ticket");
            System.out.println("3.Find ticket");
            System.out.println("4.Escalate ticket");
            System.out.println("5.Resolve ticket");
            System.out.println("6.Delete ticket");
            System.out.println("7.Exit");

            int n = sc.nextInt();

            switch (n) {
                case 1:
                    obj.print();
                    break;

                case 2:
                    int id = sc.nextInt();
                    String name = sc.next();
                    String issue = sc.next();
                    String priority = sc.next();
                    obj.add(id, name, issue, priority);
                    break;

                case 3:
                    obj.findTicket(sc.nextInt());
                    break;

                case 4:
                    obj.escalateTicket(sc.nextInt());
                    break;

                case 5:
                    obj.resolveTicket(sc.next());
                    break;

                case 6:
                    obj.delete(sc.nextInt());
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
