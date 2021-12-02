import java.text.DecimalFormat;
import java.util.*;


class Faculty{
    String name;
    Student[] students;

    public Faculty(String name, Student [] students) {
        this.name = name;
        this.students = Arrays.copyOf(students, students.length);
    }

    public int countStudentsFromCity(String cityName) {
        int i=0;
        for (Student student : students) {
            if(student.getCity().equals(cityName))
                i++;
        }
        return i;
    }

    public Student getStudent(long index) {
        for (Student student : students) {
            if(student.getIndex() == index)
                return student;
        }
        return null;
    }
    public double getAverageNumberOfContacts() {
        double suma = 0.0;
        for (Student student : students) {
            suma+=student.getNumberOfContacts();
        }
        return suma / students.length;
    }

    public Student getStudentWithMostContacts() {
        Student max = students[0];
        for (Student student : students) {
            int res = Integer.compare(student.getNumberOfContacts(), max.getNumberOfContacts());
            if(res > 0) {
                max = student;
            }else if(res == 0) {
                if(student.getIndex() > max.getIndex())
                    max = student;
            }
        }
        return max;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{\"fakultet\":\"%s\", ", this.name));
        sb.append("\"studenti\":[");
        for (Student student : students) {
            sb.append(student + ", ");
        }
        String s = sb.toString();
        if(students.length != 0)
            s = s.substring(0, sb.length() -2);
        s += "]}";
        return s;
    }
}

abstract class Contact implements  Comparable<Contact>
{
    protected String date;

    public Contact(String date)
    {
        this.date=  date;

    }

    public boolean isNewerThan (Contact c)
    {
        return (this.date.compareTo(c.date)) > 0;
    }

    public abstract String getType();

    @Override
    public int compareTo(Contact o)
    {
        return this.date.compareTo(o.date);
    }
}

class EmailContact extends Contact {
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return "Email";
    }

    @Override
    public String toString() {
        return this.email;
    }
}

class PhoneContact extends Contact{
    public enum Operator{
        VIP, ONE, TMOBILE
    }

    private String num;


    public PhoneContact(String date, String num) {
        super(date);
        this.num = num;
    }
    public String getPhone() {
        return this.num;
    }
    public Operator getOperator() {
        if(num.charAt(2) == '0' || num.charAt(2) =='1' || num.charAt(2) == '2')
            return Operator.TMOBILE;
        if(num.charAt(2) == '5' || num.charAt(2) =='6')
            return Operator.ONE;
        if(num.charAt(2) == '7' || num.charAt(2) =='8' )
            return Operator.VIP;
        return null;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return "Phone";
    }

    @Override
    public String toString() {
        return this.num;
    }
}

class Student{
    String firstName, lastName, city;
    int age;
    long index;

    List<Contact> contacts;

    public Student(String firstName, String lastName, String city, int age, long index) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        contacts = new ArrayList<>();
    }

    public int getNumberOfContacts() {
        return contacts.size();
    }
    public void addEmailContact(String date, String email) {
        contacts.add(new EmailContact(date, email));
    }
    public void addPhoneContact(String date, String phone) {
        contacts.add(new PhoneContact(date, phone));
    }

    public Contact[] getEmailContacts(){
        Contact[] result = new Contact[contacts.size()];

        int i = 0;
        for (Contact c : contacts)
        {
            if(c.getType().equals("Email"))
                result[i++] = c;
        }
        result = Arrays.copyOf(result, i);

        return result;
    }

    public Contact[] getPhoneContacts() {
        Contact[] result = new Contact[contacts.size()];
        int i=0;
        for (Contact c : contacts) {
            if(c.getType().equals("Phone"))
                result[i++] = c;
        }
        result = Arrays.copyOf(result, i);
        return result;
    }
    public String getCity() {
        return this.city;
    }
    public String getFullName() {
        return this.firstName + this.lastName;
    }
    public long getIndex() {
        return this.index;
    }

    public Contact getLatestContact(){
        return Collections.max(contacts);
    }

    public String toStringEmails() {
        String s="";
        for (Contact contact : getEmailContacts()) {
            s += String.format("\"%s\", ", contact);
        }
        if(getEmailContacts().length != 0)
            s = s.substring(0, s.length()-2);
        return s;
    }
    public String toStringPhones() {
        String s="";
        for (Contact contact : getPhoneContacts()) {
            s += String.format("\"%s\", ", contact);
        }
        if(getPhoneContacts().length != 0)
            s = s.substring(0, s.length()-2);
        return s;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(String.format("\"ime\":\"%s\", ", this.firstName));
        sb.append(String.format("\"prezime\":\"%s\", ", this.lastName));
        sb.append(String.format("\"vozrast\":%d, ", this.age));
        sb.append(String.format("\"grad\":\"%s\", ", this.city));
        sb.append(String.format("\"indeks\":%d, ", this.index));
        sb.append(String.format("\"telefonskiKontakti\":[%s], ", this.toStringPhones()));
        sb.append(String.format("\"emailKontakti\":[%s]", this.toStringEmails()));
        sb.append("}");
        return sb.toString();

    }
}
public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0&&faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }
        }

        scanner.close();
    }
}