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

                context.success("Prueba");
                // ScanovateSdk.start(this.cordova.getActivity(),
                // param1, param2, param3, param4, param5, param6, param7, param8, param9, new ScanovateHandler(){
                //     public void onSuccess(CloseResponse response, int code, String uuidDevice) {
                //         context.success(response.getTransactionId());
                //     }

                //      @Override
                //      public void onFailure(CloseResponse closeResponse) {
                //          String calificacion = closeResponse.getExtras().getStateName() + " " + closeResponse.getExtras().getAdditionalProp1();
                //          context.error("Resultado de Transacción: " + calificacion);
                //      }
                // });
            }catch(Exception ex){
                context.error("Se ha presentado un error al ejecutar la acción 'start', el detalle de la exepción a continuación: " + ex);
            }
        }else{
            context.error("Debe ingresar los argumentos necesarios para continuar");
        }
    }

    private void evaluateTransaction(String transactionId, CallbackContext context) {
        
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.validateTransaction("avistaqa", transactionId, new ApiHelper.ValidateTransactionHandler() {
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
}
