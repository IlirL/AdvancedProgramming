import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde

class Category{

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class NewsItem{
    private String title;
    private Date objavuvanje;
    private Category category;

    public NewsItem(String title, Date objavuvanje, Category category) {
        this.title = title;
        this.objavuvanje = objavuvanje;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public Date getObjavuvanje() {
        return objavuvanje;
    }

    public Category getCategory() {
        return category;
    }

    public String getTeaser()
    {
        return "";
    }
}

class TextNewsItem extends NewsItem{

    private String text;

    public TextNewsItem(String title, Date objavuvanje, Category category, String text) {
        super(title, objavuvanje, category);
        this.text = text;
    }
    @Override
    public String getTeaser()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle() + "\n");
        Date now = new Date();
        long minutes = (now.getTime() - getObjavuvanje().getTime()) / 60000;
        sb.append(minutes + "\n");
        sb.append(text.substring(0, Math.min(80, text.length())) + "\n");

        return sb.toString();


    }
}

class MediaNewsItem extends NewsItem{
    private int views;
    private String url;

    public MediaNewsItem(String title, Date objavuvanje, Category category, String url, int views) {
        super(title, objavuvanje, category);
        this.views = views;
        this.url = url;
    }
    @Override
    public String getTeaser()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle() + "\n");
        Date now = new Date();
        long minutes = (now.getTime() - getObjavuvanje().getTime()) / 60000;
        sb.append(minutes + "\n");
        sb.append(url+ "\n");
        sb.append(views+ "\n");

        return sb.toString();
    }
}

class FrontPage{
    private ArrayList<NewsItem> newsItems;
    private Category[] categories;

    public FrontPage(Category[] categories)
    {
        this.categories = Arrays.copyOf(categories, categories.length);
        this.newsItems = new ArrayList<NewsItem>();
    }

    public void addNewsItem(NewsItem newsItem){
        newsItems.add(newsItem);

    }

    public List<NewsItem> listByCategory(Category category)
    {
        return newsItems.stream()
                .filter(newsItem -> newsItem.getCategory().equals(category))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if(Arrays.stream(categories).noneMatch(category1 -> category1.getName().equals(category)))
            throw new CategoryNotFoundException(category);

        return newsItems.stream()
                .filter(newsItem -> newsItem.getCategory().getName().equals(category))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<this.newsItems.size(); i++)
            sb.append(newsItems.get(i).getTeaser());
        return sb.toString();
    }
}

class CategoryNotFoundException  extends Exception{
    public CategoryNotFoundException(String category) {
        super(String.format("Category %s was not found", category));
    }
}