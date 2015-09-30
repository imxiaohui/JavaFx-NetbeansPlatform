package com.asgteach.familytree.eventnew;
/** Localizable strings for {@link com.asgteach.familytree.eventnew}. */
@javax.annotation.Generated(value="org.netbeans.modules.openide.util.NbBundleProcessor")
class Bundle {
    /**
     * @param person person
     * @return <i>Create New Event for </i>{@code person}
     * @see NewEventWizardAction
     */
    static String CTL_NewEventDialogTitle(Object person) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_NewEventDialogTitle", person);
    }
    /**
     * @return <i>New &amp;Event</i>
     * @see NewEventWizardAction
     */
    static String CTL_NewEventWizardAction() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_NewEventWizardAction");
    }
    /**
     * @return <i>Event Type</i>
     * @see Visual1FXController
     */
    static String CTL_Panel1Name() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel1Name");
    }
    /**
     * @param Birth_or_Adopt Birth or Adopt
     * @param String_targetperson_ String(targetperson)
     * @return <i>A </i>{@code Birth_or_Adopt}<i> record already exists for </i>{@code String_targetperson_}
     * @see Wizard1EventTypePerson
     */
    static String CTL_Panel1_DuplicateRecordErr(Object Birth_or_Adopt, Object String_targetperson_) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel1_DuplicateRecordErr", Birth_or_Adopt, String_targetperson_);
    }
    /**
     * @param String_dupPerson_ String(dupPerson)
     * @param String_dupEvent_ String(dupEvent)
     * @return <i>Person = </i>{@code String_dupPerson_}<i>, Event = </i>{@code String_dupEvent_}
     * @see Wizard1EventTypePerson
     */
    static String CTL_Panel1_DuplicateRecordErrDetail(Object String_dupPerson_, Object String_dupEvent_) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel1_DuplicateRecordErrDetail", String_dupPerson_, String_dupEvent_);
    }
    /**
     * @return <i>Persons Database is empty, you must create Person data first</i>
     * @see Wizard1EventTypePerson
     */
    static String CTL_Panel1_NoPeople() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel1_NoPeople");
    }
    /**
     * @return <i>Date and Place</i>
     * @see Visual2FXController
     */
    static String CTL_Panel2Name() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel2Name");
    }
    /**
     * @return <i>Date field cannot be empty.</i>
     * @see Visual2FXController
     */
    static String CTL_Panel2_EmptyDate() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel2_EmptyDate");
    }
    /**
     * @return <i>Set Relationships and Review</i>
     * @see Visual3FXController
     */
    static String CTL_Panel3Name() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel3Name");
    }
    /**
     * @param String_NewPerson_ String(NewPerson)
     * @param String_OldPerson_ String(OldPerson)
     * @param String_Eventname String Eventname
     * @return {@code String_NewPerson_}<i> added to </i>{@code String_OldPerson_}<i>s </i>{@code String_Eventname}<i> Record</i>
     * @see Visual3FXController
     */
    static String CTL_Panel3_InfoEventUpdate(Object String_NewPerson_, Object String_OldPerson_, Object String_Eventname) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel3_InfoEventUpdate", String_NewPerson_, String_OldPerson_, String_Eventname);
    }
    /**
     * @param String_ExPerson_ String(ExPerson)
     * @return <i>Please specify an Ex for </i>{@code String_ExPerson_}
     * @see Visual3FXController
     */
    static String CTL_Panel3_UnknownExErr(Object String_ExPerson_) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel3_UnknownExErr", String_ExPerson_);
    }
    /**
     * @param String_SpousePerson_ String(SpousePerson)
     * @return <i>Please specify a Spouse for </i>{@code String_SpousePerson_}
     * @see Visual3FXController
     */
    static String CTL_Panel3_UnknownSpouseErr(Object String_SpousePerson_) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_Panel3_UnknownSpouseErr", String_SpousePerson_);
    }
    private void Bundle() {}
}
