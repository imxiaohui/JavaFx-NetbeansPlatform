package com.asgteach.familytree.utilities;
/** Localizable strings for {@link com.asgteach.familytree.utilities}. */
@javax.annotation.Generated(value="org.netbeans.modules.openide.util.NbBundleProcessor")
class Bundle {
    /**
     * @return <i>&amp;Delete</i>
     * @see DeleteNodeAction
     */
    static String CTL_DeleteAction() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_DeleteAction");
    }
    /**
     * @return <i>Happy Mammoth Action</i>
     * @see EventCapabilityNode
     */
    static String CTL_HappyMammothTitle() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_HappyMammothTitle");
    }
    /**
     * @param person person
     * @return <i>Select Image for </i>{@code person}
     * @see PersonCapabilityNode
     */
    static String CTL_OpenImageFile(Object person) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_OpenImageFile", person);
    }
    /**
     * @param person person
     * @return <i>Image stored for </i>{@code person}
     * @see PersonCapabilityNode
     */
    static String CTL_SaveImageMsg(Object person) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_SaveImageMsg", person);
    }
    /**
     * @param file_size file size
     * @return <i>Please select a file that is less than </i>{@code file_size}<i> bytes</i>
     * @see PersonCapabilityNode
     */
    static String ERRMSG_FileTooBig(Object file_size) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "ERRMSG_FileTooBig", file_size);
    }
    /**
     * @return <i>First name:</i>
     * @see PersonType
     */
    static String LBL_NewFirst_dialog() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_NewFirst_dialog");
    }
    /**
     * @return <i>Last name:</i>
     * @see PersonType
     */
    static String LBL_NewLast_dialog() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_NewLast_dialog");
    }
    /**
     * @return <i>New Person</i>
     * @see PersonType
     */
    static String TITLE_NewPerson_dialog() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "TITLE_NewPerson_dialog");
    }
    private void Bundle() {}
}
