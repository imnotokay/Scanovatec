package cordova.plugin.scanovatec;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import mabel_tech.com.scanovate_demo.ScanovateHandler;
import mabel_tech.com.scanovate_demo.ScanovateSdk;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.network.ApiHelper;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;

/**
 * This class echoes a string called from JavaScript.
 */
public class Scanovatec extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            this.start(args, callbackContext);
            return true;
        }else if (action.equals("evaluateTransaction")){
            this.evaluateTransaction(args, callbackContext);
            return true;
        }
        return false;
    }

    private void start(JSONArray args, CallbackContext context){
        if(args != null){
            try{
                String param1 = args.getJSONObject(0).getString("param1");
                int param2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));
                String param3 = args.getJSONObject(0).getString("param3");
                String param4 = args.getJSONObject(0).getString("param4");
                String param5 = args.getJSONObject(0).getString("param5");
                String param6 = args.getJSONObject(0).getString("param6");
                boolean param7 = Boolean.parseBoolean(args.getJSONObject(0).getString("param7"));
                String param8 = args.getJSONObject(0).getString("param8");
                String param9 = args.getJSONObject(0).getString("param9");

                
                ScanovateSdk.start(this.cordova.getActivity(),
                param1, param2, param3, param4, param5, param6, param7, param8, param9, new ScanovateHandler(){
                    public void onSuccess(CloseResponse response, int code, String uuidDevice) {
                        String jsonObject = convertToJson(response);
                        evaluateTransaction(response.getTransactionId(), param3, jsonObject, context);
                    }

                     @Override
                     public void onFailure(CloseResponse closeResponse) {
                         String calificacion = closeResponse.getExtras().getStateName() + " " + closeResponse.getExtras().getAdditionalProp1();
                         context.error("Resultado de Transacción: " + calificacion);
                     }
                });
            }catch(Exception ex){
                context.error("Se ha presentado un error al ejecutar la acción 'start', el detalle de la exepción a continuación: " + ex);
            }
        }else{
            context.error("Debe ingresar los argumentos necesarios para continuar");
        }
    }
    
    private void evaluate(JSONArray args, CallbackContext context) {
        String transactionId = args.getJSONObject(0).getString("transactionId");
        String username = args.getJSONObject(0).getString("username");
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.validateTransaction(username, transactionId, new ApiHelper.ValidateTransactionHandler() {
            @Override
            public void onSuccess(String stateName) {
                context.success(stateName);
            }

            @Override
            public void onConnectionFailed() {
                context.error("Resultado de Transacción: Se ha perdido la conexión al momento de consultar la transacción");
            }

            @Override
            public void onFailure(int i, String s) {
                context.error("Resultado de Transacción: Algo fallo al momento de consultar la transaccion");
            }
        }, this.cordova.getActivity());
    }

    private void evaluateTransaction(String transactionId, String username, String jsonObject, CallbackContext context) {
        
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.validateTransaction(username, transactionId, new ApiHelper.ValidateTransactionHandler() {
            @Override
            public void onSuccess(String stateName) {
                context.success(jsonObject);
            }

            @Override
            public void onConnectionFailed() {
                context.error("Resultado de Transacción: Se ha perdido la conexión al momento de consultar la transacción");
            }

            @Override
            public void onFailure(int i, String s) {
                context.error("Resultado de Transacción: Algo fallo al momento de consultar la transaccion");
            }
        }, this.cordova.getActivity());
    }
    
    private String convertToJson(CloseResponse response){
        String json = "{";
        json += " \"Uid\": \"" + response.getUid() + "\",";
        json += " \"StartingDate\": \"" + response.getStartingDate() + "\",";
        json += " \"CreationDate\": \"" + response.getCreationDate() + "\",";
        json += " \"CreationIP\": \"" + response.getCreationIP() + "\",";
        json += " \"DocumentType\": \"" + response.getDocumentType() + "\",";
        json += " \"IdNumber\": \"" + response.getIdNumber() + "\",";
        json += " \"FirstName\": \"" + response.getFirstName() + "\",";
        json += " \"SecondName\": \"" + response.getSecondName() + "\",";
        json += " \"FirstSurname\": \"" + response.getFirstSurname() + "\",";
        json += " \"SecondSurname\": \"" + response.getSecondSurname() + "\",";
        json += " \"Gender\": \"" + response.getGender() + "\",";
        json += " \"BirthDate\": \"" + response.getBirthDate() + "\",";
        json += " \"Street\": \"" + response.getStreet() + "\",";
        json += " \"CedulateCondition\": \"" + response.getCedulateCondition() + "\",";
        json += " \"Spouse\": \"" + response.getSpouse() + "\",";
        json += " \"Home\": \"" + response.getHome() + "\",";
        json += " \"MaritalStatus\": \"" + response.getMaritalStatus() + "\",";
        json += " \"DateOfIdentification\": \"" + response.getDateOfIdentification() + "\",";
        json += " \"DateOfDeath\": \"" + response.getDateOfDeath() + "\",";
        json += " \"MarriageDate\": \"" + response.getMarriageDate() + "\",";
        json += " \"Instruction\": \"" + response.getInstruction() + "\",";
        json += " \"PlaceBirth\": \"" + response.getPlaceBirth() + "\",";
        json += " \"Nationality\": \"" + response.getNationality() + "\",";
        json += " \"MotherName\": \"" + response.getMotherName() + "\",";
        json += " \"FatherName\": \"" + response.getFatherName() + "\",";
        json += " \"HouseNumber\": \"" + response.getHouseNumber() + "\",";
        json += " \"Profession\": \"" + response.getProfession() + "\",";
        json += " \"ExpeditionCity\": \"" + response.getExpeditionCity() + "\",";
        json += " \"ExpeditionDepartment\": \"" + response.getExpeditionDepartment() + "\",";
        json += " \"BirthCity\": \"" + response.getBirthCity() + "\",";
        json += " \"BirthDepartment\": \"" + response.getBirthDepartment() + "\",";
        json += " \"TransactionType\": \"" + response.getTransactionType() + "\",";
        json += " \"TransactionTypeName\": \"" + response.getTransactionTypeName() + "\",";
        json += " \"IssueDate\": \"" + response.getIssueDate() + "\",";
        json += " \"AdoProjectId\": \"" + response.getAdoProjectId() + "\",";
        json += " \"TransactionId\": \"" + response.getTransactionId() + "\",";
        json += " \"ProductId\": \"" + response.getProductId() + "\",";
        json += " \"Extras\": { \"additionalPro1\": \"" + response.getExtras().getAdditionalProp1() + "\",\"additionalProp2\":\"" + response.getExtras().getAdditionalProp2() + "\", \"additionalProp3\": \"" + response.getExtras().getAdditionalProp3() + "\", \"IdState\": \"" + response.getExtras().getIdState() + "\", \"StateName\": \"" + response.getExtras().getStateName() + "\" },";
        json += " \"NumberPhone\": \"" + response.getNumberPhone() + "\",";
        json += " \"DactilarCode\": \"" + response.getDactilarCode() + "\",";
        json += "\"ResponseControlList\": " + (response.getReponseControlList() == null ? "false" : (response.getReponseControlList() ? "true" : "false")) + ",";
        json += " \"Response_ANI\": \"" + response.getResponse_ANI() + "\"";
        json += "}";

        return json;
    }
}