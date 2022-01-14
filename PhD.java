package a1;

/** NetId: gec83, ddl58. Time spent: 6 hours, 30 minutes. <br>
 * What I thought about this assignment: <br>
 * <br>
 * An instance maintains info about the PhD of a person. */

public class PhD {
    /** name of person with PhD */
    private String name;
    /** month PhD was awarded. In 1..12, with 1 meaning January */
    private int month;
    /** year PhD was awarded (4-digit). */
    private int year;
    /** first advisor of person with PhD */
    private PhD firstAdvisor;
    /** second advisor of person with PhD */
    private PhD secondAdvisor;
    /** number of PhD advisees of the person with the PhD */
    private int numAdvisees;

    /* GROUP A */

    /** Constructor: an instance for a person with name n, PhD month m, PhD year y. <br>
     * Its advisors are unknown, and it has no advisees. <br>
     * Precondition: n has at least 1 char, m is in 1..12, and y > 1000 */
    public PhD(String n, int m, int y) {
        assert n != null && n.length() >= 1;
        assert m >= 1 && m <= 12;
        assert y > 1000;
        name= n;
        month= m;
        year= y;
    }

    /** Return the name of this person */
    public String name() {
        return name;
    }

    /** Return the date on which this person got the PhD. <br>
     * In the form "month/year", with no blanks, e.g. "6/2007" */
    public String date() {
        return month + "/" + year;
    }

    /** Return the first advisor of this PhD (null if unknown). */
    public PhD advisor1() {
        return firstAdvisor;
    }

    /** Return the second advisor of this PhD (null if unknown or non-existent). */
    public PhD advisor2() {
        return secondAdvisor;
    }

    /** return number of PhD advisees of this person */
    public int numAdvisees() {
        return numAdvisees;
    }

    /* GROUP B */

    /** Add p as the first advisor of this person. <br>
     * Precondition: the first advisor is unknown and p is not null */
    public void addAdvisor1(PhD p) {
        assert firstAdvisor == null;
        assert p != null;
        firstAdvisor= p;
        p.numAdvisees++ ;
    }

    /** Add p as the second advisor of this person. <br>
     * Precondition: The first advisor (of this person) is known, the second advisor <br>
     * is unknown, p is not null, and p is different from the first advisor */
    public void addAdvisor2(PhD p) {
        assert firstAdvisor != null;
        assert secondAdvisor == null;
        assert p != null;
        assert firstAdvisor != p;
        secondAdvisor= p;
        p.numAdvisees++ ;
    }

    /* GROUP C */

    /** Constructor: a PhD with name n, PhD month m, PhD year y, first advisor <br>
     * adv1, and second advisor adv2. <br>
     * Precondition: n has at least 1 char, m is in 1..12, y > 1000, <br>
     * adv1 and adv2 are not null, and adv1 and adv2 are different. */
    public PhD(String n, int m, int y, PhD adv1, PhD adv2) {
        this(n, m, y);
        assert adv1 != null && adv2 != null;
        assert adv1 != adv2;
        firstAdvisor= adv1;
        adv1.numAdvisees++ ;
        secondAdvisor= adv2;
        adv2.numAdvisees++ ;
    }

    /* GROUP D */

    /** Return value of "this PhD has at least one advisee", <br>
     * i.e. true if this PhD has at least one advisee and false otherwise */
    public boolean hasAdvisee() {
        return numAdvisees >= 1 ? true : false;
    }

    /** Return value of "this person and p are intellectual siblings." Precondition: p is not
     * null. */
    public boolean areSiblings(PhD p) {
        assert this != p;
        assert p != null;
        boolean t1p1= firstAdvisor == p.firstAdvisor && firstAdvisor != null;
        boolean t1p2= firstAdvisor == p.secondAdvisor && firstAdvisor != null;
        boolean t2p1= secondAdvisor == p.firstAdvisor && secondAdvisor != null;
        boolean t2p2= secondAdvisor == p.secondAdvisor && secondAdvisor != null;

        return t1p1 || t1p2 || t2p1 || t2p2 ? true : false;
    }

    /** Return value of "p is not null and this person got the PhD after p." */
    public boolean gotAfter(PhD p) {
        assert p != null;
        double t_time= year + (double) month / 12;
        double p_time= p.year + (double) p.month / 12;
        return t_time - p_time > 0 && p != null ? true : false;
    }

    PhD Grace= new PhD("Grace", 4, 2008);Grace.gotAfter(PhD Chauncey);

}
