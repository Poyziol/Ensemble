package fun;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;
import javax.script.*;

public class Functions 
{
    public static ScriptEngine attribuer_valeur_js(Object my_object, String new_name)
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.put(new_name, my_object);

        return engine;
    }

    public static ScriptEngine accumuler_valeur_js(ScriptEngine my_engine, Object my_object, String new_name)
    {
        //System.out.println(new_name);
        //System.out.println(my_object);
        my_engine.put(new_name, my_object);

        return my_engine;
    }

    public static boolean evaluer_condition_js(String requete,ScriptEngine engine)
    {
        try 
        {
            Boolean result = (Boolean) engine.eval(requete);
            //System.out.println("Résultat de la condition : " + result);

            return result;
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }

        return false;
    }
    
}
