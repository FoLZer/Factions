package com.massivecraft.factions.perms;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.util.TL;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Role implements Permissible {
    ADMIN(2, TL.ROLE_ADMIN),
    MODERATOR(1, TL.ROLE_MODERATOR),
    NORMAL(0, TL.ROLE_NORMAL);

    public final int value;
    public final String nicename;
    public final TL translation;
    private Set<String> roleNamesAtOrBelow;
    private Set<String> roleNamesAtOrAbove;

    Role(final int value, final TL translation) {
        this.value = value;
        this.nicename = translation.toString();
        this.translation = translation;
    }

    public boolean isAtLeast(Role role) {
        return this.value >= role.value;
    }

    public boolean isAtMost(Role role) {
        return this.value <= role.value;
    }

    public static Role getRelative(Role role, int relative) {
        return Role.getByValue(role.value + relative);
    }

    public static Role getByValue(int value) {
        switch (value) {
            case 0:
                return NORMAL;
            case 1:
                return MODERATOR;
            case 2:
                return ADMIN;
        }

        return null;
    }

    public static Role fromString(String check) {
        switch (check.toLowerCase()) {
            case "admin":
                return ADMIN;
            case "mod":
            case "moderator":
                return MODERATOR;
            case "normal":
            case "member":
                return NORMAL;
        }

        return null;
    }

    @Override
    public String toString() {
        return this.nicename;
    }

    public TL getTranslation() {
        return translation;
    }

    public String getPrefix() {
        if (this == Role.ADMIN) {
            return FactionsPlugin.getInstance().conf().factions().prefixes().getAdmin();
        }

        if (this == Role.MODERATOR) {
            return FactionsPlugin.getInstance().conf().factions().prefixes().getMod();
        }

        if (this == Role.NORMAL) {
            return FactionsPlugin.getInstance().conf().factions().prefixes().getNormal();
        }
        return "";
    }

    @Override
    public ChatColor getColor() {
        return Relation.MEMBER.getColor();
    }

    /**
     * Gets this role name and roles above it in priority. These names are
     * not localized and will always match the enum values.
     *
     * @return an immutable set of role names
     */
    public Set<String> getRoleNamesAtOrAbove() {
        if (this.roleNamesAtOrAbove == null) {
            Set<String> set = new HashSet<>();
            for (Role role : values()) {
                if (this.isAtMost(role)) {
                    set.add(role.name().toLowerCase());
                }
            }
            this.roleNamesAtOrAbove = Collections.unmodifiableSet(set);
        }
        return this.roleNamesAtOrAbove;
    }

    /**
     * Gets this role name and roles below it in priority. These names are
     * not localized and will always match the enum values.
     *
     * @return an immutable set of role names
     */
    public Set<String> getRoleNamesAtOrBelow() {
        if (this.roleNamesAtOrBelow == null) {
            Set<String> set = new HashSet<>();
            for (Role role : values()) {
                if (this.isAtLeast(role)) {
                    set.add(role.name().toLowerCase());
                }
            }
            this.roleNamesAtOrBelow = Collections.unmodifiableSet(set);
        }
        return this.roleNamesAtOrBelow;
    }
}
