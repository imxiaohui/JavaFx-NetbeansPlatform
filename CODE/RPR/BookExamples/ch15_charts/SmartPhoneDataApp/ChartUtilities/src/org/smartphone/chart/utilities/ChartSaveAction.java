/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.smartphone.chart.utilities.ChartSaveAction"
)
@ActionRegistration(
        iconBase = "org/smartphone/chart/utilities/saveChartIcon.png",
        displayName = "#CTL_ChartSaveAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1300),
    @ActionReference(path = "Toolbars/File", position = 200)
})
@Messages({
    "CTL_ChartSaveAction=Save Chart",
    "# {0} - windowname",
    "MSG_SAVE_DIALOG=Save {0}",
    "# {0} - Filename",
    "MSG_SaveFailed=Could not write to file {0}",
    "# {0} - Filename",
    "MSG_Overwrite=File {0} exists. Overwrite?"
})
public final class ChartSaveAction implements ActionListener {

    private final ChartSaveCapability context;
    private static final Logger logger = Logger.getLogger(ChartSaveAction.class.getName());

    public ChartSaveAction(ChartSaveCapability context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // use a FileChooser to get a user-supplied filename
        String title = "Save " + context.getChartName() + " to File";
        File f = new FileChooserBuilder(
                ChartSaveAction.class).setTitle(title).showSaveDialog();
        if (f != null) {
            if (!f.getAbsolutePath().endsWith(".png")) {
                f = new File(f.getAbsolutePath() + ".png");
            }
            try {
                if (!f.exists()) {
                    // the file doesn't exist; create it
                    if (!f.createNewFile()) {
                        DialogDisplayer.getDefault().notify(
                                new NotifyDescriptor.Message(
                                        Bundle.MSG_SaveFailed(f.getName())));
                        return;
                    }
                } else {
                    // the file exists; asks if it's okay to overwrite
                    Object userChose = DialogDisplayer.getDefault().notify(
                            new NotifyDescriptor.Confirmation(
                                    Bundle.MSG_Overwrite(f.getName())));
                    if (NotifyDescriptor.CANCEL_OPTION.equals(userChose)) {
                        return;
                    }
                }
                // Need getAbsoluteFile(), 
                // or X.png and x.png are different on windows
                BufferedImage image = context.getImage();
                if (image != null) {
                    ImageIO.write(image, "png", f.getAbsoluteFile());
                    logger.log(Level.INFO, "Image saved to file {0}", f.getName());
                } else {
                    logger.log(Level.WARNING, "Could not get Image from {0}", context.getChartName());
                }
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
        }
    }
}
