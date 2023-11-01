package enums;

import interfaces.PropertyEnum;

public enum BookProperty implements PropertyEnum {
    ISBN (1, "ISBN", "isbn"),
    TITLE (2, "Title", "title"),
    YEAR (3, "Year", "year"),
    AUTHOR (4, "Author", "author.name"),
    PUBLISHER(5, "Publisher", "publisher.name"),
    INSTANCES (6, "Instances", "instances"),
    LEND_INSTANCES (7, "Lend Instances", "lendInstances"),
    LEFT_INSTANCES (8, "Left Instances", "leftInstances");

    final int val;
    final String prettyName;
    final String queryName;

    BookProperty(int val, String prettyName, String queryName) {
        this.prettyName = prettyName;
        this.val = val;
        this.queryName = queryName;
    }

    public int getVal() {
        return this.val;
    }

    public String getPrettyName() {
        return this.prettyName;
    }

    public String getQueryName() {
        return this.queryName;
    }
}
