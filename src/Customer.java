import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Customer {
    private int ID;
    private String name;
    private String email;
    private String adress;
    private String phoneNumb;
    private String membership;
    private String username;
    private String password;

    public  Customer(){
    }
    public Customer(int ID, String name,String email, String adress, String phoneNumb, String membership, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.adress = adress;
        this.phoneNumb = phoneNumb;
        this.membership = membership;
        this.username = username;
        this.password = password;
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void registerMember() throws IOException {
        String line, ID, username, password, fileName, name, email, adress, phoneNumb;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name:");
        name = scanner.nextLine();
        while (true){
            System.out.println("Enter email");
            email = scanner.nextLine();
            if(email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")){
                break;
            }else{
                System.out.println("Wrong format for an email. Please enter email again");
            }
        }
        System.out.println("Enter adress: ");
        adress = scanner.nextLine();
        while (true){
            System.out.println("Enter phone number:");
            phoneNumb = scanner.nextLine();
            if(phoneNumb.matches("^\\d{10}$")){
                break;
            } else {
                System.out.println("Wrong format for a phone number. Please enter phone number again");
            }
        }
        while (true){
            System.out.println("Enter username:");
            username = scanner.nextLine();
            if(!checkUsername(username)){
                break;
            } else {
                System.out.println("This username has already used! Please enter different username");
            }
        }
        while (true){
            System.out.println("Enter password:");
            password = scanner.nextLine();
            if(!checkPassword(password)){
                break;
            } else {
                System.out.println("This password has already used! Please enter different username");
            }
        }
        System.out.println("Register successful");
        int count = 0;
        fileName = "./src/File/customers.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(fileName,true));
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,",");
            count++;
        }
        count += 1;
        pw.println(count +","+name + "," + email + "," + adress + "," + phoneNumb + "," + "none" + "," + username + "," + password);
        pw.close();
        fileScanner.close();
    }
    private boolean checkUsername(String inputUsername) throws IOException {
        String line, ID, username, password, fileName, name, email, adress, phoneNumb, membership;
        Boolean usernameExist = false;
        fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,",");
            ID = inReader.nextToken();
            name = inReader.nextToken();
            email = inReader.nextToken();
            adress = inReader.nextToken();
            phoneNumb = inReader.nextToken();
            membership = inReader.nextToken();
            username = inReader.nextToken();
            password = inReader.nextToken();
            if (inputUsername.equals(username)) {
                usernameExist = true;
                System.out.println("Username already exist");
                break;
            }
        }
        return usernameExist;
    }

    private boolean checkPassword(String inputPassword) throws IOException {
        String line, ID, username, password, fileName, name, email, adress, phoneNumb, membership;
        Boolean passwordExist = false;
        fileName = "D:/Java project/group asm/customer.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,",");
            ID = inReader.nextToken();
            name = inReader.nextToken();
            email = inReader.nextToken();
            adress = inReader.nextToken();
            phoneNumb = inReader.nextToken();
            membership = inReader.nextToken();
            username = inReader.nextToken();
            password = inReader.nextToken();
            if (inputPassword.equals(password)) {
                passwordExist = true;
                System.out.println("Password already exist");
                break;
            }
        }
        return passwordExist;
    }
    public void login(String inputUsername, String inputPassword) throws IOException {
        String line, ID, username, password, fileName, name, email, adress, phoneNumb, membership;
        Boolean loggedin = false;
        fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNextLine()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,",");
            ID = inReader.nextToken();
            name = inReader.nextToken();
            email = inReader.nextToken();
            adress = inReader.nextToken();
            phoneNumb = inReader.nextToken();
            membership = inReader.nextToken();
            username = inReader.nextToken();
            password = inReader.nextToken();
            if (inputUsername.equals(username) && inputPassword.equals(password)) {
                loggedin = true;
                System.out.println("successful login");
                break;
            }
        }
        if(!loggedin){
            System.out.println("Incorrect username or password");
        }
        fileScanner.close();
    }
    public void displayAccountInfo() throws IOException{
        String line, ID, username, password, fileName, name, email, adress, phoneNumb,membership;
        fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,",");
            ID = inReader.nextToken();
            name = inReader.nextToken();
            email = inReader.nextToken();
            adress = inReader.nextToken();
            phoneNumb = inReader.nextToken();
            membership = inReader.nextToken();
            username = inReader.nextToken();
            password = inReader.nextToken();
            if (getUsername().equals(username)) {
                System.out.println("ID:"+ ID + " Name:" + name + " Email:" + email + " Adress:" + adress + " Phone number:" + phoneNumb + " Membership:"+ membership + " Username:" + username);
                break;
            }
        }
    }

    static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line = reader.readLine();

            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent

            String newContent = oldContent.replaceAll(oldString, newString);

            //Rewriting the input text file with newContent

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void updateAccountInfo()throws IOException{
        String line, ID, username, password, fileName, name, email, adress, phoneNumb, membership, newData, newName, newEmail, newAdress, newPhoneNumb ;
        fileName = "./src/File/customers.txt";
        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,",");
            ID = inReader.nextToken();
            name = inReader.nextToken();
            email = inReader.nextToken();
            adress = inReader.nextToken();
            phoneNumb = inReader.nextToken();
            membership = inReader.nextToken();
            username = inReader.nextToken();
            password = inReader.nextToken();
            if (getUsername().equals(username)) {
                System.out.println("Do you want to update your name?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askName = scanner.nextLine();
                if(askName.equals("1")){
                    System.out.println("Enter new name");
                    name = scanner.nextLine();
                }
                System.out.println("Do you want to update your email?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askEmail = scanner.nextLine();
                if(askEmail.equals("1")){
                    System.out.println("Enter new email");
                    email = scanner.nextLine();
                }
                System.out.println("Do you want to update your adress?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askAdress = scanner.nextLine();
                if(askAdress.equals("1")){
                    System.out.println("Enter new adress");
                    adress = scanner.nextLine();
                }
                System.out.println("Do you want to update your phone number?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askPhoneNumb = scanner.nextLine();
                if(askPhoneNumb.equals("1")){
                    System.out.println("Enter new phone number");
                    phoneNumb = scanner.nextLine();
                }

                newData = ID + "," + name + "," + email + "," + adress + "," + phoneNumb + ","+ membership + "," + username + "," + password;
                modifyFile(fileName,line,newData);
                break;
            }
        }
        fileScanner.close();
    }

    public void checkMembership()throws IOException{
        String line, ID, username, password, fileName, name, email, adress, phoneNumb,membership;
        fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line, ",");
            ID = inReader.nextToken();
            name = inReader.nextToken();
            email = inReader.nextToken();
            adress = inReader.nextToken();
            phoneNumb = inReader.nextToken();
            membership = inReader.nextToken();
            username = inReader.nextToken();
            password = inReader.nextToken();
            if(getUsername().equals(username)){
                System.out.println("Membership: "+membership);
                break;
            }
        }

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public void setPhoneNumb(String phone) {
        this.phoneNumb = phone;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
