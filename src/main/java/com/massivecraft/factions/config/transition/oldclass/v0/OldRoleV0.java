package com.massivecraft.factions.config.transition.oldclass.v0;

import com.massivecraft.factions.perms.Permissible;
import com.massivecraft.factions.perms.Role;
import com.massivecraft.factions.util.TL;

public enum OldRoleV0 implements OldPermissableV0 {
    ADMIN(2, TL.ROLE_ADMIN, Role.ADMIN),
    MODERATOR(1, TL.ROLE_MODERATOR, Role.MODERATOR),
    NORMAL(0, TL.ROLE_NORMAL, Role.NORMAL);

    public final int value;
    public final String nicename;
    public final TL translation;
    public final Role replacement;

    OldRoleV0(final int value, final TL translation, final Role replacement) {
        this.value = value;
        this.nicename = translation.toString();
        this.translation = translation;
        this.replacement = replacement;
    }

    public Permissible newPermissible() {
        return this.replacement;
    }

    public static OldRoleV0 fromString(String check) {
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
}
