package aff;

import dtb.*;
import java.util.Vector;

public class Main 
{
    public static void main(String[] poyz) throws Exception
    {
        System.out.println();

        //Insertion de donnees
        Vector<String> tab_attribut1= new Vector<String>();              //Attribut
        tab_attribut1.add("Nom");
        tab_attribut1.add("Age");
        tab_attribut1.add("est_celibataire");

        Vector<Object> tab_object1 = new Vector<Object>();
        tab_object1.add(String.class);
        Vector<Object> tab_object2 = new Vector<Object>();
        tab_object2.add(Integer.class);
        tab_object2.add(String.class);
        Vector<Object> tab_object3 = new Vector<Object>();
        tab_object3.add(true);
        tab_object3.add(false);
        tab_object3.add(String.class);
        tab_object3.add(0);
        tab_object3.add(1);

        Vector<String> tab_attribut2= new Vector<String>();          
        tab_attribut2.add("Prenom");
        tab_attribut2.add("Experience");
        tab_attribut2.add("Aime_doritos");

        Vector<Vector<Object>> uplet = new Vector<Vector<Object>>();    //Uplet
        Vector<Vector<Object>> uplet2 = new Vector<Vector<Object>>();

        Ensemble ensemble1 = new Ensemble(tab_object1);
        //ensemble1.show_elements();
        Ensemble ensemble2 = new Ensemble(tab_object2);
        //ensemble2.show_elements();
        Ensemble ensemble3 = new Ensemble(tab_object3);
        //ensemble3.show_elements();

        Vector<Ensemble> tab_domaine = new Vector<Ensemble>();          //Domaine
        tab_domaine.add(ensemble1);
        tab_domaine.add(ensemble2);
        tab_domaine.add(ensemble3);

        Relation table1 = new Relation("Personne",tab_attribut1, uplet, tab_domaine);
        Relation table2 = new Relation("Animal",tab_attribut2, uplet2, tab_domaine);

        Vector<Object> new_element1 = new Vector<Object>();             //Insertion uplet
        new_element1.add("Nijika");
        new_element1.add(17);
        new_element1.add(true);
        table1.insert(new_element1);

        Vector<Object> new_element2 = new Vector<Object>();
        new_element2.add(null);
        new_element2.add(19);
        new_element2.add(false);
        table1.insert(new_element2);
        
        Vector<Object> new_element3 = new Vector<Object>();
        new_element3.add("Nijika");
        new_element3.add(5);
        new_element3.add(true);
        table2.insert(new_element3);

        Vector<Object> new_element4 = new Vector<Object>();
        new_element4.add("Nijika");
        new_element4.add(7);
        new_element4.add(null);
        table2.insert(new_element4);


//--------------------------------------------------------------------------------------------------------------------------------------------------

        //Appel de fonctions dans relation
        table1.show_relation();
        table2.show_relation();
        System.out.println();
        System.out.println();

        //table1.projection("Nom,est_celibataire");                                 //Projection
        //System.out.println();
        //System.out.println();

        //System.out.println("Union:");
        //Relation table1_u_table2 = table1.union(table2);                            //Union
        //System.out.println();
        //System.out.println();

        //Vector<Ensemble> ensemble_union = table1_u_table2.get_domaine();
        //ensemble_union.get(0).show_elements();
        //ensemble_union.get(1).show_elements();
        //ensemble_union.get(2).show_elements();
        //System.out.println();
        //System.out.println();

        //System.out.println("Intersection:");
        //Relation table1_n_table2 = table1.intersection(table2);                     //Intersection
        //System.out.println();
        //System.out.println();

        //Vector<Ensemble> ensemble_intersection = table1_n_table2.get_domaine();
        //ensemble_intersection.get(0).show_elements();
        //ensemble_intersection.get(1).show_elements();
        //ensemble_intersection.get(2).show_elements();
        //System.out.println();
        //System.out.println();

        //Relation selection1 = table1.selection("est_celibataire = true");            //Selection
        //System.out.println();
        //System.out.println();

        //Relation table1_x_table2 = table1.produit_cartesien(table2);                //Produit Cartesien
        //System.out.println();
        //System.out.println();

        Relation table1_µ_table2 = table1.teta_jointure("Nom = Prenom",table2);
        System.out.println();
        System.out.println();

        //table2 = table1.add_null_to_relation(table2);
        //System.out.println();
        //System.out.println();

        //Relation table1_x_table2_g = table1.produit_cartesien_externe_gauche(table2);
        //System.out.println();
        //System.out.println();

        //Relation table1_x_table2_d = table1.produit_cartesien_externe_droite(table2);
        //System.out.println();
        //System.out.println();

        //Relation table1_x_table2_e = table1.produit_cartesien_externe(table2);
        //System.out.println();
        //System.out.println();

        //Relation table1_µ_table2_g = table1.teta_jointure_externe_gauche("Nom = Prenom",table2);
        //System.out.println();
        //System.out.println();

        //Relation table1_µ_table2_d = table1.teta_jointure_externe_droite("Nom = Prenom",table2);
        //System.out.println();
        //System.out.println();

        //Relation table1_µ_table2_e = table1.teta_jointure_externe("Nom = Prenom",table2);
        //System.out.println();
        //System.out.println();

        System.out.println();
    }
}
