package com.pixelz.client.alt;

/**
 * Wurst-style Alt - mirrors Wurst AltManager (name, email, cracked/premium, starred).
 * For 1.21.11, cracked = offline UUID, premium = accessToken (Microsoft).
 * Password not stored in plain for Microsoft - use token if available.
 */
public class Alt {
    private String name; // in-game username
    private String email = ""; // for premium (Microsoft email)
    private String password = ""; // or token - not used for cracked
    private boolean cracked = true;
    private boolean starred = false;

    public Alt() {} // for Gson

    public Alt(String name, String email, String password, boolean cracked) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cracked = cracked;
    }

    public static Alt cracked(String name) {
        return new Alt(name, "", "", true);
    }

    public static Alt premium(String name, String email, String token) {
        Alt a = new Alt(name, email, token, false);
        return a;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isCracked() { return cracked; }
    public void setCracked(boolean cracked) { this.cracked = cracked; }
    public boolean isStarred() { return starred; }
    public void setStarred(boolean starred) { this.starred = starred; }

    public String getDisplayName() {
        return (starred ? "★ " : "") + name + (cracked ? " (Cracked)" : " (" + email + ")");
    }
}
