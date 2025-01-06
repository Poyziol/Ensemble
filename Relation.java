package dtb;

import fun.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@SuppressWarnings("rawtypes")
public class Relation 
{
    String nom;
    Vector<String> attribute;
    Vector<Vector<Object>> uplet;
    Vector<Ensemble> domaine;

    public Relation(String nom1,Vector<String> attribute1,Vector<Vector<Object>> uplet1,Vector<Ensemble> domaine1)
    {
        this.nom = nom1;
        this.attribute = attribute1;
        this.uplet = uplet1;
        this.domaine = domaine1;
    }

    public Relation(Vector<String> attribute1,Vector<Vector<Object>> uplet1,Vector<Ensemble> domaine1)
    {
        this.attribute = attribute1;
        this.uplet = uplet1;
        this.domaine = domaine1;
    }

    public Relation() {}

    public String get_nom()
    {
        return this.nom;
    }

    public void set_nom(String new_nom)
    {
        this.nom = new_nom;
    }

    public Vector<String> get_attribut()
    {
        return this.attribute;
    }

    public Vector<String> set_attribut(Vector<String> new_attribut)
    {
        this.attribute = new_attribut;
        return this.attribute;
    }

    public Vector<Vector<Object>> get_uplet()
    {
        return this.uplet;
    }

    public void set_uplet(Vector<Vector<Object>> new_uplet)
    {
        this.uplet = new_uplet;
    }

    public Vector<Ensemble> get_domaine()
    {
        return this.domaine;
    }

    public void set_domaine(Vector<Ensemble> new_domaine)
    {
        this.domaine = new_domaine;
    }

    public void show_relation()
    {
        System.out.println();
        
        for(int ni = 0;ni < attribute.size();ni++)
        {
            System.out.print(this.get_attribut().get(ni) + "\t\t");
        }

        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println();

        Vector tmp = new Vector();
        for(int ji = 0;ji < uplet.size();ji++)
        {
            tmp = get_uplet().get(ji);
            for(int ka = 0;ka < tmp.size();ka++)
            {
                System.out.print(tmp.get(ka) + "\t\t");
            }
            System.out.println();
        }
    }

    public void show_relation_plus_grande()
    {
        System.out.println();
        
        for(int ni = 0;ni < attribute.size();ni++)
        {
            System.out.print(this.get_attribut().get(ni) + "\t\t");
        }

        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println();

        Vector tmp = new Vector();
        for(int ji = 0;ji < uplet.size();ji++)
        {
            tmp = get_uplet().get(ji);
            for(int ka = 0;ka < tmp.size();ka++)
            {
                System.out.print(tmp.get(ka) + "\t\t    ");
            }
            System.out.println();
        }
    }

    public void insert(Vector<Object> new_uplet)
    {
        Vector<Vector<Object>> tab_uplet = this.get_uplet();
        Vector<Ensemble> tab_domaine = this.get_domaine();
        int ni = 0;
        int ji = 0;
        Object problem = new Object();

        if (new_uplet.size() == this.get_attribut().size()) 
        {
            for(Object uplet_tmp : new_uplet)
            { 
                if(tab_domaine.get(ji).appartenance(uplet_tmp) == true)
                {
                    ni++;
                }
                else
                {
                    problem = uplet_tmp;
                }
                ji++;
            }
            if(ni == new_uplet.size())
            {
                tab_uplet.add(new_uplet);
                this.set_uplet(tab_uplet);
                System.out.println("Insertion reussie");
            }
            else
            {
                System.out.println(problem + " n est pas element du domaine");
            }
        } 
        else 
        {
            System.out.println("Erreur : Le nombre d'attribute a inserer ne correspond pas a celui de la table choisit.");
        }
    }

    public Relation enlever_doublure()
    {
        Vector<Vector<Object>> tab_uplet = this.get_uplet();
        HashSet<Vector<Object>> tab_filtre = new HashSet<Vector<Object>>(tab_uplet);

        Vector<Vector<Object>> new_tab_uplet = new Vector<Vector<Object>>(tab_filtre);

        this.set_uplet(new_tab_uplet);
        return this;
    }

    public Relation projection(String requete)
    {
        requete = requete.trim();
        String[] tab_string = requete.split(",");
        Vector<String> attribute_used = new Vector<String>();

        for(int ni = 0;ni < tab_string.length;ni++)
        {
            attribute_used.add(tab_string[ni].trim());
        }

        Vector<String> attribute = this.get_attribut();
        Vector<Integer> tab_id_tmp = new Vector<Integer>();
        Vector<Integer> tab_id_unused = new Vector<Integer>();

        for(int ni = 0;ni < attribute.size();ni++)
        {
            String attribute_tmp = attribute.get(ni);
            for(int ji = 0;ji < attribute_used.size();ji++)
            {
                String attribute_used_tmp = attribute_used.get(ji);
                if(attribute_tmp.equals(attribute_used_tmp))
                {
                    tab_id_tmp.add(ni);
                }
            }
        }

        for(int ka = 0;ka < attribute.size();ka++)
        {
            int val = 0;
            for(int i = 0;i < tab_id_tmp.size();i++)
            {
                int id_tmp = tab_id_tmp.get(i);
                if(id_tmp != ka)
                {
                    val++;
                }
                if(id_tmp == ka)
                {
                    break;
                }
            }
            if(val == tab_id_tmp.size())
            {
                tab_id_unused.add(ka);
            }
        }

        Vector<String> new_attribute = this.get_attribut();
        Vector<Vector<Object>> new_uplet = this.get_uplet();
        Vector<Ensemble> new_domaine = this.get_domaine();


        for(int res = 0;res < new_attribute.size();res++)
        {
            for(int chiji = 0;chiji < tab_id_unused.size();chiji++)
            {
                if(res == tab_id_unused.get(chiji))
                {
                    new_attribute.remove(res);
                    new_domaine.remove(res);
                }
            }
        }

        for(int ni = 0;ni < new_uplet.size();ni++)
        {
            for(int ji = 0;ji < new_uplet.get(ni).size();ji++)
            {
                for(int ka = 0;ka < tab_id_unused.size();ka++)
                {
                    if(ji == tab_id_unused.get(ka))
                    {
                        new_uplet.get(ni).remove(ji);
                    }
                }
            }
        }

        Relation new_relation = new Relation(this.get_nom(),new_attribute,new_uplet,new_domaine);
        new_relation = new_relation.enlever_doublure();

        new_relation.show_relation();
        return new_relation;
    }

    public Relation union(Relation relation2)
    {
        Vector<String> tab_attribut1 = this.get_attribut();
        Vector<String> tab_attribut2 = relation2.get_attribut();

        Vector<Vector<Object>> tab_uplet1 = this.get_uplet();
        Vector<Vector<Object>> tab_uplet2 = relation2.get_uplet();

        Vector<Ensemble> tab_ensemble1 = this.get_domaine();
        Vector<Ensemble> tab_ensemble2 = relation2.get_domaine();
        Vector<Ensemble> new_tab_ensemble = new Vector<Ensemble>();

        Relation new_relation = new Relation(this.get_nom(), this.get_attribut(), this.get_uplet(), this.get_domaine());

        if(tab_attribut1.size() == tab_attribut2.size())
        {
            for(Vector<Object> tab_object_tmp : tab_uplet2)
            {
                tab_uplet1.add(tab_object_tmp);
            }

            for(int ni = 0;ni < tab_ensemble1.size();ni++)
            {
                Ensemble ensemble1_tmp = tab_ensemble1.get(ni);
                Ensemble ensemble2_tmp = tab_ensemble2.get(ni);

                new_tab_ensemble.add(ensemble1_tmp.union(ensemble2_tmp));
            }

            new_relation.set_uplet(tab_uplet1);
            new_relation.set_domaine(new_tab_ensemble);
        }
        else
        {
            System.out.println("Le nombre de colonnes n est pas la meme");
        }

        new_relation.enlever_doublure();
        new_relation.show_relation();
        return new_relation;
    }

    public Relation intersection(Relation relation2)
    {
        Vector<String> tab_attribut1 = this.get_attribut();
        Vector<String> tab_attribut2 = relation2.get_attribut();

        Vector<Vector<Object>> tab_uplet1 = this.get_uplet();
        Vector<Vector<Object>> tab_uplet2 = relation2.get_uplet();

        Vector<Vector<Object>> new_tab_uplet = new Vector<Vector<Object>>();

        Relation new_relation = new Relation(this.get_nom(), this.get_attribut(), this.get_uplet(), this.get_domaine());

        if(tab_attribut1.size() == tab_attribut2.size())
        {
            for(Vector<Object> tab_object_tmp : tab_uplet2)
            {
                if(tab_uplet1.contains(tab_object_tmp))
                {
                    new_tab_uplet.add(tab_object_tmp);
                }
            }

            new_relation.set_uplet(new_tab_uplet);
        }
        else
        {
            System.out.println("Le nombre de colonnes n est pas la meme");
        }

        new_relation.show_relation();
        return new_relation;
    }

    public String transformer_requete_en_condition(String requete) 
    {
        String regex1 = "(?<![<>!])=";
        String regex2 = "AND";
        String regex3 = "OR";
   
        String transformed = requete.replaceAll(regex1, "==");
        transformed = transformed.replaceAll(regex2, "&&");
        transformed = transformed.replaceAll(regex3, "||");
    
        return transformed;
    }

    public Vector<Object> get_objects_column(int index)
    {
        Relation my_relation = new Relation(this.get_nom(),this.get_attribut(),this.get_uplet(),this.get_domaine());
        Vector<Vector<Object>> tab_uplet = my_relation.get_uplet();
        Vector<Object> new_tab_column = new Vector<Object>();

        for(Vector<Object> mini_tab_uplet : tab_uplet)
        {
            new_tab_column.add(mini_tab_uplet.get(index));
        }

        return new_tab_column;
    }
    
    public Relation selection(String requete)
    {
        Relation my_relation = new Relation(this.get_nom(),this.get_attribut(),this.get_uplet(),this.get_domaine());
        Relation new_relation = new Relation(this.get_nom(),this.get_attribut(),new Vector<Vector<Object>>(),this.get_domaine());

        String condition = transformer_requete_en_condition(requete);

        Vector<Vector<Object>> tab_uplet = my_relation.get_uplet();
        Vector<Vector<Object>> new_tab_uplet = new_relation.get_uplet();

        Vector<String> tab_attribut = get_attribut();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine my_engine = manager.getEngineByName("JavaScript");

        for(Vector<Object> mini_tab_uplet : tab_uplet)
        {
            for(int ni = 0;ni < mini_tab_uplet.size();ni++)
            {
                Object uplet_tmp = mini_tab_uplet.get(ni);
                String attribut_tmp = tab_attribut.get(ni);

                my_engine = Functions.accumuler_valeur_js(my_engine,uplet_tmp,attribut_tmp);
            }

            if(Functions.evaluer_condition_js(condition,my_engine))
            {
                new_tab_uplet.add(mini_tab_uplet);
            }
        }

        new_relation.set_uplet(new_tab_uplet);
        //new_relation.show_relation();

        return new_relation;
    }

    public Relation produit_cartesien(Relation rel2)
    {
        Relation rel1 = this;

        Vector<String> new_attribute = new Vector<String>();
        Vector<Vector<Object>> new_uplet = new Vector<Vector<Object>>();
        Vector<Ensemble> new_domaine = new Vector<Ensemble>();

        for(int ni = 0; ni < rel1.get_attribut().size(); ni++) 
        {
            new_attribute.add(rel1.get_attribut().get(ni));
        }
        for(int ji = 0; ji < rel2.get_attribut().size(); ji++) 
        {
            new_attribute.add(rel2.get_attribut().get(ji));
        }

        for(int ni = 0; ni < rel1.get_domaine().size(); ni++) 
        {
            new_domaine.add(rel1.get_domaine().get(ni));
        }
        for(int ji = 0; ji < rel2.get_domaine().size(); ji++) 
        {
            new_domaine.add(rel2.get_domaine().get(ji));
        }

        for(Vector<Object> mini_tab_uplet : rel1.get_uplet()) 
        {
            for(Vector<Object> mini_tab_uplet2 : rel2.get_uplet()) 
            {
                Vector<Object> mini_tab_uplet_tmp = new Vector<>(mini_tab_uplet);
                for(Object uplet_tmp : mini_tab_uplet2) 
                {
                    mini_tab_uplet_tmp.add(uplet_tmp);
                }

                new_uplet.add(mini_tab_uplet_tmp);
            }
        }

        Relation new_rel = new Relation(new_attribute, new_uplet, new_domaine);
        //new_rel.show_relation_plus_grande();
        return new_rel;
    }

    public Vector<Object> get_null_line(Relation rel2)
    {
        Vector<Vector<Object>> tab_uplet = rel2.get_uplet();
        Vector<Object> tab_null = new Vector<Object>();
        for(Vector<Object> mini_tab_uplet : tab_uplet)
        {
            for(Object tab_uplet_tmp : mini_tab_uplet)
            {
                tab_null.add(null);
            }
            break;
        }

        return tab_null;
    }

    public Relation add_null_to_relation(Relation rel_inverse)
    {
        Relation rel_origine = this;

        String my_name = rel_inverse.get_nom();
        Vector<String> my_attribute = rel_inverse.get_attribut();
        Vector<Vector<Object>> new_uplet_inverse = rel_inverse.get_uplet();
        Vector<Ensemble> my_domaine = rel_inverse.get_domaine();
        Vector<Object> tab_null = get_null_line(rel_inverse); 

        int check_null = 0;
        for(Vector<Object> tab_uplet_origine : rel_origine.get_uplet())
        {
            for(Object uplet_tmp_origine : tab_uplet_origine)
            {
                if(uplet_tmp_origine == null)
                {
                    new_uplet_inverse.add(tab_null);
                    check_null = 1;
                    break;
                }
            }
            if(check_null == 1) 
            {
                break;
            }
        }
        
        Relation new_relation_inverse = new Relation(my_name, my_attribute, new_uplet_inverse, my_domaine);
        //new_relation_inverse.show_relation();
        return new_relation_inverse;
    }

    public Relation produit_cartesien_externe_droite(Relation rel_droite)
    {
        Relation rel_gauche = this;
        rel_gauche = rel_droite.add_null_to_relation(rel_gauche);
        Relation table1_x_table2 = rel_gauche.produit_cartesien(rel_droite);

        Vector<Object> tab_null = table1_x_table2.get_null_line(table1_x_table2);
        Vector<Vector<Object>> new_uplet = table1_x_table2.get_uplet();
        for(int ni = 0;ni < table1_x_table2.get_uplet().size();ni++)
        {
            Vector<Object> tab_uplet = table1_x_table2.get_uplet().get(ni);
            if(tab_null.equals(tab_uplet))
            {
                new_uplet.remove(ni);
                break;
            }
        }

        table1_x_table2.set_uplet(new_uplet);
        //table1_x_table2.show_relation();
        return table1_x_table2;
    }

    public Relation produit_cartesien_externe_gauche(Relation rel_droite)
    {
        Relation rel_gauche = this;
        rel_droite = rel_gauche.add_null_to_relation(rel_droite);
        Relation table1_x_table2 = rel_gauche.produit_cartesien(rel_droite);

        Vector<Object> tab_null = table1_x_table2.get_null_line(table1_x_table2);
        Vector<Vector<Object>> new_uplet = table1_x_table2.get_uplet();
        for(int ni = 0;ni < table1_x_table2.get_uplet().size();ni++)
        {
            Vector<Object> tab_uplet = table1_x_table2.get_uplet().get(ni);
            if(tab_null.equals(tab_uplet))
            {
                new_uplet.remove(ni);
                break;
            }
        }
        

        table1_x_table2.set_uplet(new_uplet);
        //table1_x_table2.show_relation();
        return table1_x_table2;
    }

    public Relation produit_cartesien_externe(Relation rel_droite)
    {
        Relation rel_gauche = this;
        rel_droite = rel_gauche.add_null_to_relation(rel_droite);
        rel_gauche = rel_droite.add_null_to_relation(rel_gauche);
        Relation table1_x_table2 = rel_gauche.produit_cartesien(rel_droite);

        Vector<Object> tab_null = table1_x_table2.get_null_line(table1_x_table2);
        Vector<Vector<Object>> new_uplet = table1_x_table2.get_uplet();
        for(int ni = 0;ni < table1_x_table2.get_uplet().size();ni++)
        {
            Vector<Object> tab_uplet = table1_x_table2.get_uplet().get(ni);
            if(tab_null.equals(tab_uplet))
            {
                new_uplet.remove(ni);
                break;
            }
        }
        

        table1_x_table2.set_uplet(new_uplet);
        //table1_x_table2.show_relation();
        return table1_x_table2;
    }

    public Relation teta_jointure(String condition,Relation rel2)
    {
        Relation rel1 = new Relation(this.get_nom(),this.get_attribut(),this.get_uplet(),this.get_domaine());

        Relation new_relation = rel1.produit_cartesien(rel2);
        new_relation = new_relation.selection(condition);

        new_relation.show_relation_plus_grande();
        return new_relation;
    }

    public Relation teta_jointure_externe_gauche(String condition,Relation rel2)
    {
        Relation rel1 = new Relation(this.get_nom(),this.get_attribut(),this.get_uplet(),this.get_domaine());

        Relation new_relation = rel1.produit_cartesien_externe_gauche(rel2);
        new_relation = new_relation.selection(condition);

        new_relation.show_relation_plus_grande();
        return new_relation;
    }

    public Relation teta_jointure_externe_droite(String condition,Relation rel2)
    {
        Relation rel1 = new Relation(this.get_nom(),this.get_attribut(),this.get_uplet(),this.get_domaine());

        Relation new_relation = rel1.produit_cartesien_externe_droite(rel2);
        new_relation = new_relation.selection(condition);

        new_relation.show_relation_plus_grande();
        return new_relation;
    }

    public Relation teta_jointure_externe(String condition,Relation rel2)
    {
        Relation rel1 = new Relation(this.get_nom(),this.get_attribut(),this.get_uplet(),this.get_domaine());

        Relation new_relation = rel1.produit_cartesien_externe(rel2);
        new_relation = new_relation.selection(condition);

        new_relation.show_relation_plus_grande();
        return new_relation;
    }

    public Relation division(Relation rel2)
    {
        Relation rel1 = this;

        Vector<String> new_attribute = new Vector<String>();
        Vector<Vector<Object>> new_uplet = new Vector<Vector<Object>>();
        Vector<Ensemble> new_domaine = new Vector<Ensemble>();

        
    }
}

