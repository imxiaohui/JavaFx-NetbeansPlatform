package com.asgteach.familytree.persontimeline;
/** Localizable strings for {@link com.asgteach.familytree.persontimeline}. */
@javax.annotation.Generated(value="org.netbeans.modules.openide.util.NbBundleProcessor")
class Bundle {
    /**
     * @return <i>PersonTimeLine</i>
     * @see PersonTimeLineTopComponent
     */
    static String CTL_PersonTimeLineAction() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_PersonTimeLineAction");
    }
    /**
     * @param person person
     * @return {@code person}<i> TimeLine</i>
     * @see PersonTimeLineTopComponent
     */
    static String CTL_PersonTimeLineTopComponent(Object person) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_PersonTimeLineTopComponent", person);
    }
    /**
     * @return <i>This is a PersonTimeLine window</i>
     * @see PersonTimeLineTopComponent
     */
    static String HINT_PersonTimeLineTopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HINT_PersonTimeLineTopComponent");
    }
    private void Bundle() {}
}
