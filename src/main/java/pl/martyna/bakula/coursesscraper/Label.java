package pl.martyna.bakula.coursesscraper;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

enum Label {
    JAVA("Java", List.of("Java", "Java - Programowanie", "RxJava", "JPA")),
    SPRING("Spring", List.of("Spring", "Spring Boot", "Spring Framework")),
    KOTLIN("Kotlin", List.of("Kotlin")),
    PYTHON("Python", List.of("Python", "Python - Programowanie", "Python Scripting", "PyCharm")),
    WORDPRESS("WordPress", List.of("WordPress", "WordPress Gutenberg Editor", "WordPress for Ecommerce")),
    UNITY("Unity", List.of("Unity")),
    JAVASCRIPT("JavaScript", List.of("JavaScript", "jQuery", "React JS", "React.JS", "Node JS", "Node.JS", "Vue JS", "Vue.JS", "Typescript", "React Native")),
    PHP("PHP", List.of("PHP", "Laravel")),
    C("C", List.of("C", "C - Programowanie", "C (programming language)")),
    C_SHARP("C#", List.of("C#", "C# - Programowanie", "ASP.NET Core", "ASP.NET")),
    C_PLUS_PLUS("C++", List.of("C++", "C++ - Programowanie")),
    BAZY_DANYCH("Bazy danych", List.of("Bazy danych", "SQL", "PL/SQL", "MySQL", "SQLite", "MongoDB", "SQL Server", "PostgreSQL", "Database Management", "Big Data", "Oracle Database", "Database Administration")),
    ANGULAR("Angular", List.of("Angular", "AngularJS")),
    HTML("HTML", List.of("HTML", "HTML5", "PSD to HTML")),
    CSS("CSS", List.of("CSS")),
    GIT("Git", List.of("GIT", "GitHub", "Git Remote")),
    SWIFT("Swift", List.of("Swift")),
    OTHER("Inne", List.of());

    private final String text;
    private final List<String> keys;

    Label(String text, List<String> keys) {
        this.text = text;
        this.keys = keys;
    }

    public String getText() {
        return text;
    }

    public List<String> getKeys() {
        return keys;
    }

    public static Label getLabelByKey(String courseLabel) {
        for (Label element : Label.values()) {
            for (String key : element.getKeys()) {
                if (courseLabel.equalsIgnoreCase(key)) {
                    return element;
                }
            }
        }
        return OTHER;
    }

    public static Label getLabelByText(String labelText) {
        for (Label element : Label.values()) {
            if (element.getText().equals(labelText)) {
                return element;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
