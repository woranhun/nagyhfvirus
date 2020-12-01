package simulatorComponents;

import java.io.Serializable;

/**
 * Enum, ami a pottyok tipusat tarolja.
 * Megvalositja a Serializable interfeszt
 */
public enum dotTypes implements Serializable {
    /**
     * Nincs: hiba kezeles miatt
     */
    None,
    /**
     * Egeszseges
     */
    Healthy,
    /**
     * Fertozo: Lappangasi ido utan meghalhat vagy meggyugyulhat
     */
    Infectious,
    /**
     * Semleges: Sima szemely
     */
    Neutral,
    /**
     * Halott
     */
    Dead
}