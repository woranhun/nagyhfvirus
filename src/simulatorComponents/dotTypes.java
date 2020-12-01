package simulatorComponents;

import java.io.Serializable;

/**
 * Enum, ami a pöttyök típusát tárolja.
 * Megvalósítja a Serializable interfészt
 */
public enum dotTypes implements Serializable {
    /**
     * Nincs: hiba kezelés miatt
     */
    None,
    /**
     * Egészséges
     */
    Healthy,
    /**
     * Fertőző: Lappangási idő után meghalhat vagy meggyúgyulhat
     */
    Infectious,
    /**
     * Semleges: Sima személy
     */
    Neutral,
    /**
     * Halott
     */
    Dead
}