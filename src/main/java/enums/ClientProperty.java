package enums;

import interfaces.PropertyEnum;

public enum ClientProperty implements PropertyEnum {
    DOCUMENT (1, "Document", "document"),
    FIRST_NAME (2, "First Name", "firstName"),
    LAST_NAME (3, "Last Name", "lastName"),
    PHONE (4, "Phone", "phone");

    final int val;
    final String prettyName;
    final String queryName;

    ClientProperty(int val, String prettyName, String queryName) {
        this.val = val;
        this.prettyName = prettyName;
        this.queryName = queryName;
    }

    public int getVal() {
        return val;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public String getQueryName() {
        return queryName;
    }
}
