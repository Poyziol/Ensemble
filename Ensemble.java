package dtb;

import java.util.HashSet;
import java.util.Vector;

public class Ensemble 
{
    Vector<Object> elements;
    

    public Ensemble(Vector<Object> elements1)
    {
        this.elements = elements1;
    }

    public Ensemble() {}

    public Vector<Object> get_elements()
    {
        return this.elements;
    }

    public void set_elements(Vector<Object> new_elements)
    {
        this.elements = new_elements;
    }

    public void show_elements()
    {
        Vector<Object> elements1 = this.get_elements();
        for(int ni = 0;ni < elements1.size();ni++)
        {
            System.out.print(elements1.get(ni) + "       ");
        }
        System.out.println();
        System.out.println("_______________________________________________________________________________________________");
    }
    
    public boolean appartenance(Object new_element) 
    {
        Vector<Object> tab_element = this.get_elements();

        if(new_element == null)
        {
            return true;
        }
        for(Object element : tab_element) 
        {
            if(element instanceof Class) 
            {
                if(((Class<?>) element).isInstance(new_element)) 
                {
                    return true;
                }
            } 
            else 
            {
                if(element.equals(new_element)) 
                {
                    return true;
                }
            }
        }
        
        return false;
    }

    public Ensemble enlever_doublure()
    {
        Ensemble pseudo_ensemble = this;
        Vector<Object> tab_element = this.get_elements();
        HashSet<Object> tab_filter = new HashSet<Object>(tab_element);

        Vector<Object> new_tab_element = new Vector<Object>(tab_filter);
        pseudo_ensemble.set_elements(new_tab_element);

        return pseudo_ensemble;
    }

    public Ensemble union(Ensemble ensemble2)
    {
        Vector<Object> result = new Vector<Object>();
        Vector<Object> elements1 = this.get_elements();
        Vector<Object> elements2 = ensemble2.get_elements();
        Ensemble ensemble_result = new Ensemble();

        for(int ni=0;ni<elements1.size();ni++)
        {
            result.add(elements1.get(ni));
        }

        for(int ji=0;ji<elements2.size();ji++)
        {
            Object object_tmp = elements2.get(ji);
            if(this.appartenance(object_tmp) == false)
            {
                result.add(object_tmp);
            }
        }
        
        ensemble_result.set_elements(result);
        ensemble_result = ensemble_result.enlever_doublure();
        return ensemble_result;
    }

    public Ensemble intersection(Ensemble ensemble2)
    {
        Vector<Object> result = new Vector<Object>();
        Vector<Object> elements1 = this.get_elements();
        Ensemble ensemble_result = new Ensemble();

        for(int ni = 0;ni < elements1.size();ni++)
        {
            Object object_tmp = elements1.get(ni);
            if(this.appartenance(object_tmp) == true)
            {
                result.add(object_tmp);
            }
        }
        ensemble_result.set_elements(result);
        return ensemble_result;
    }

    public Ensemble difference(Ensemble ensemble2)
    {
        
        Vector<Object> result = new Vector<Object>();
        Vector<Object> elements1 = this.get_elements();
        Ensemble ensemble_result = new Ensemble();

        for(int ni=0;ni<elements1.size();ni++)
        {
            result.add(elements1.get(ni));
        }

        for(int ji=0;ji<elements1.size();ji++)
        {
            Object object_tmp = elements1.get(ji);
            if(this.appartenance(object_tmp) == true)
            {
                result.remove(ji);
            }
        }
        ensemble_result.set_elements(result);
        return ensemble_result;
    }

}
