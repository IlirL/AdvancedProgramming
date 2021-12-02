import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

// вашиот код овде
class ArchiveStore {
    ArrayList<Archive> archives;
    StringBuilder stringBuilder;
    public ArchiveStore() {
        this.archives = new ArrayList<Archive>();
        this.stringBuilder = new StringBuilder();
    }

    public void archiveItem(Archive item, Date date)
    {
        item.setDateArchived(date);
        archives.add(item);
        stringBuilder.append(String.format("Item %d archived at ", item.id)+date + "\n");
//        System.out.println(String.format("Item %d archived at ", item.id)+date);
    }

    public void openItem(int id, Date date) throws NonExistingItemException {
        if(archives.stream()
                .anyMatch(archive -> archive.id == id))
        {
            archives.stream().filter(archive -> archive.id == id)
                    .forEach(archive -> {
                        if(archive.getType() == Type.LOCKED_ARCHIVE)
                        {
                            LockedArchive temp = (LockedArchive) archive;
                            if(temp.getDateToOpen().after(temp.dateArchived))
                            {
                                stringBuilder.append("Item "+temp.getId() +" cannot be opened before "+temp.getDateToOpen()+"\n");
//                                System.out.println("Item "+temp.getId() +"cannot be opened before "+temp.getDateToOpen());
                            }
                            else
                                stringBuilder.append("Item "+ archive.id + " opened at "+archive.dateArchived+"\n");
//                                System.out.println("Item "+ archive.id + " opened at "+archive.dateArchived);
                        }
                        else if(archive.getType() == Type.SPECIAL_ARCHIVE)
                        {
                            SpecialArchive temp = (SpecialArchive) archive;
                            if(temp.getOpened() == temp.getMaxOpen())
                            {
                                stringBuilder.append("Item " + temp.id + " cannot be opened more than "+temp.getMaxOpen()+" times"+"\n");
//                                System.out.println("Item " + temp.id+" cannot be opened more than "+temp.getMaxOpen()+" times.");

                            }
                            else{
//                                System.out.println("Item "+ archive.id + " opened at "+archive.dateArchived);
                                stringBuilder.append("Item "+ archive.id + " opened at "+archive.dateArchived+"\n");
                                ((SpecialArchive) archive).open();
                            }

                        }

                    });

        }

        else
            throw new NonExistingItemException(id);
    }

    public String getLog(){
        return stringBuilder.toString();
    }
}

class NonExistingItemException extends Exception{
    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist", id));

    }
}

class Archive
{
    protected int id;
    protected Date dateArchived;
    protected Type type;

    public Archive(int id, Date dateArchived) {
        this.id = id;
        this.dateArchived = dateArchived;
    }

    public Archive(int id)
    {
        this.id = id;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        this.dateArchived = new Date();
    }
    public int getId() {
        return id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    public Type getType()
    {
        return type;
    }
}

class LockedArchive extends  Archive{
        Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
        this.type = Type.LOCKED_ARCHIVE;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }
}

class SpecialArchive extends Archive{
        private int maxOpen;
        private int opened;
    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.type = Type.SPECIAL_ARCHIVE;
        this.opened = 0;
    }

    public void open()
    {
        this.opened++;
    }

    public int getOpened()
    {
        return opened;
    }

    public int getMaxOpen()
    {
        return maxOpen;
    }

}

enum Type{
    LOCKED_ARCHIVE,
    SPECIAL_ARCHIVE
}

