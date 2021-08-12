package DB2FX;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DB2.*;

public class DB2Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtStatement;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnSelect;

    @FXML
    private Button btnCompute;

    //@FXML
    //private TextField txtColumnNumber;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnQuit;

    @FXML
    private Label lblError;


    private DB2Test db2Object = new DB2Test();

    private String statement = new String();
    private int column;

    @FXML
    void btnDeleteOnAction(ActionEvent event)
    {
        txtStatement.setText("DELETE");
        /*statement = "";
        lblError.setText("");
        if(txtStatement.getText() != null)
        {
            statement = txtStatement.getText();
            if(db2Object.delete(statement))
            {
                lblError.setText("Successful");
            }
            else
            {
                lblError.setText("Error occurred");
            }
        }*/

    }

    @FXML
    void btnInsertOnAction(ActionEvent event)
    {
        txtStatement.setText("INSERT");
        /*statement = "";
        lblError.setText("");
        if(txtStatement.getText()!=null)
        {
            statement = txtStatement.getText();
            if(db2Object.insert(statement))
            {
                lblError.setText("Successful");
            }
            else
            {
                lblError.setText("Error occurred");
            }
        }
        */

    }

    @FXML
    void btnComputeOnAction(ActionEvent event)
    {
        lblError.setText("");
        statement = "";
        if (txtStatement.getText() != null && isSafeString(txtStatement.getText()))
        {
            statement = txtStatement.getText();
            if (statement.toUpperCase(Locale.ROOT).startsWith("INSERT"))
            {
                if (db2Object.insert(statement))
                {
                    lblError.setText("Successful");
                } else
                {
                    lblError.setText("Error occurred");
                }
            }


            if (statement.toUpperCase(Locale.ROOT).startsWith("SELECT"))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //  statement = txtStatement.getText();
                if (!db2Object.select(statement).equals("Error occurred"))
                {
                    lblError.setText("Successful");
                    String resultSelect = db2Object.select(statement);
                    alert.setHeaderText("RESULTS");
                    TextArea area = new TextArea(resultSelect);
                    area.setWrapText(true);
                    area.setEditable(false);
                    alert.getDialogPane().setExpandableContent(area);
                    alert.setResizable(true);
                    alert.setHeaderText("RESULTS");
                    alert.showAndWait();

                } else
                {
                    lblError.setText("Error occurred");
                    alert.setHeaderText("Error occurred");
                    alert.showAndWait();
                }
            }

            if (statement.toUpperCase(Locale.ROOT).startsWith("DELETE"))
            {
                if (db2Object.delete(statement))
                {
                    lblError.setText("Successful");
                } else
                {
                    lblError.setText("Error occurred");
                }
            }


        }
        else
        {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("You entered a forbidden word!!!");
            alertError.showAndWait();
        }
    }


    @FXML
    void btnQuitOnAction(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            System.exit(0);
            db2Object.closeConnection();
        }
    }

    @FXML
    void btnSelectOnAction(ActionEvent event)
    {
        txtStatement.setText("SELECT");
        /*statement = "";
        if(txtStatement.getText() != null)
        {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                statement = txtStatement.getText();
                if(!db2Object.select(statement).equals("Error occurred"))
                {
                    lblError.setText("Successful");
                    String resultSelect = db2Object.select(statement);
                    alert.setHeaderText("RESULTS");
                    TextArea area = new TextArea(resultSelect);
                    area.setWrapText(true);
                    area.setEditable(false);
                    alert.getDialogPane().setExpandableContent(area);
                    alert.setResizable(true);
                    alert.setHeaderText("RESULTS");
                    alert.showAndWait();

                }
                else
                {
                    lblError.setText("Error occurred");
                    alert.setHeaderText("Error occurred");
                    alert.showAndWait();
                }

        }

        column = 0;*/
    }


    private int  countCommas(String str)
    {
        int commas = 0;
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == ',') commas++;
        }
        return commas+1;

    }
    private boolean isSafeString(String str)
    {
        return !str.toUpperCase(Locale.ROOT).contains("DROP") && !str.toUpperCase(Locale.ROOT).contains("ALTER");
    }

    @FXML
    void txtStatementOnAction(ActionEvent event)
    {
    }

    @FXML
    void initialize() {
        assert txtStatement != null : "fx:id=\"txtStatement\" was not injected: check your FXML file 'DB2.fxml'.";
        assert btnInsert != null : "fx:id=\"btnInsert\" was not injected: check your FXML file 'DB2.fxml'.";
        assert btnSelect != null : "fx:id=\"btnSelect\" was not injected: check your FXML file 'DB2.fxml'.";
        //assert txtColumnNumber != null : "fx:id=\"txtColumnNumber\" was not injected: check your FXML file 'DB2.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'DB2.fxml'.";
        assert btnQuit != null : "fx:id=\"btnQuit\" was not injected: check your FXML file 'DB2.fxml'.";
        db2Object.openConnection();
    }

    //INSERT INTO FN71986.STAFF(STAFFID, NAME, TELEPHONE, BRANCH_NAME) VALUES(1000045, 'Elena Georgieva', 08323543343, 'Mizia')
    //DELETE FROM FN71986.CARDS WHERE CARDID = 131254
    //SELECT STAFFID, NAME, TELEPHONE, BRANCH_NAME FROM FN71986.STAFF
    //DELETE FROM FN71986.STAFF WHERE NAME = 'Alex Dimitrov'
}
