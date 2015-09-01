package com.clefeflo.assistantdenavigation;

import java.util.ArrayList;

/**
 * Created by Félix on 04/02/2015.
 */
public class MyPath {

    private static String[] chemin;
    private static int[] cheminNum;
    private static ArrayList<Liaison> liaison = new ArrayList<>(),//liste qui va contenir tous les points en attente d'�tre test�s
            liaisonSelectionne = new ArrayList<>();//liste qui va contenir tous les points selectionnes, d'o� ils viennent, leur poids total
    private static ArrayList<String> pointsDejaFaits = new ArrayList<>();//liste qui va contenir le nom des points qu'on a d�ja test�s
    private static ArrayList<Integer> liaisonNum = new ArrayList(),
            numLiaisonSelectionne = new ArrayList<>();


    public static void calcPath(String pointDepart, String pointArrivee) {
        liaison = new ArrayList<>();liaisonSelectionne = new ArrayList<>();pointsDejaFaits = new ArrayList<>();liaisonNum = new ArrayList();numLiaisonSelectionne = new ArrayList<>();
        String x, z = null;
        int y = 0, plusPetit;
        x = pointDepart;
        do {
            pointsDejaFaits.add(x); //on ajoute le point � la liste des points deja test�s
            AddToMap.calcLiaisons(x); //on lance le calcul des points reli�s
            String[] pointsConnectes = AddToMap.getConnectedPointsTo(); //on recup�re le nom des points reli�s
            int[] poidsLiaisons = AddToMap.getPoidsLiaisonTo(); //et leur distance du point x
            int[] pointNum = AddToMap.getPointNum();

            for (int i = 0; i < pointsConnectes.length; i++) {
            }

            for (int i = 0; i < poidsLiaisons.length; i++) { //on ajoute le poids de x, pour avoir leur poids total par rapport au tout premier point
                poidsLiaisons[i] = poidsLiaisons[i] + y;
            }
            for (int i = 0; i < pointsConnectes.length; i++) { //pour le nombre de points connect�s � x
                boolean found = false;
                for (int j = 0; j < liaison.size(); j++) { //on compare � chacun des points en attente de traitement
                    if (liaison.get(j).getPoint1().equals(pointsConnectes[i])) { //si le point est le m�me
                        found = true; //le point est d�j� dans la liste
                        if (liaison.get(j).getPoids() > poidsLiaisons[i]) { //si le nouveau point pr�sente un chemin plus court
                            liaison.set(j, new Liaison(pointsConnectes[i], x, poidsLiaisons[i])); //on remplace d'o� il vient et le poids total par les nouveaux trouv�s
                            liaisonNum.set(j, pointNum[i]);
                        }
                    }
                }
                if (!found) { //si le point est pas d�j� dans la liste
                    liaison.add(new Liaison(pointsConnectes[i], x, poidsLiaisons[i])); //on le rajoute
                    liaisonNum.add(pointNum[i]);
                }
            }

            do
            { //on cherche le points le moins loin du tout premier point dans la liste de points � traiter
                plusPetit = 0; //par d�fault, le premier numero de la liste
                for (int i = 0; i < liaison.size(); i++) { //on teste tous les point � traiter un par un
                    if (liaison.get(plusPetit).getPoids() > liaison.get(i).getPoids()) //si on en trouve un plus Petit
                        plusPetit = i; //on stocke son numero
                }
            }
            while (testDejaFait(plusPetit)); //on appelle la fonction testDejaFait, et on lui envoie le numero trouv�. Si d�ja fait, on boucle

            x = liaison.get(plusPetit).getPoint1(); //on stocke les param�tres du nouveau point
            z = liaison.get(plusPetit).getPoint2();
            y = liaison.get(plusPetit).getPoids();

            liaisonSelectionne.add(new Liaison(x, z, y)); //on ajoute le point � l'Liaison des selectionn�s
            numLiaisonSelectionne.add(liaisonNum.get(plusPetit));
        }
        while (!x.equals(pointArrivee)); //on boucle tant que le point selectionn� n'est pas celui de fin

        ArrayList<String> cheminInverse = new ArrayList<>();
        ArrayList<Integer> cheminInverseNum = new ArrayList<>();
        while (!x.equals(pointDepart)) { //on recherche le chemin (cette liste est invers�e, on remonte les donn�es jusqu'� la source)
            cheminInverse.add(x); //on ajoute le point au chemin
            for (int i = 0; i < liaisonSelectionne.size(); i++) {
                if (liaisonSelectionne.get(i).getPoint1().equals(x)) {
                    x = liaisonSelectionne.get(i).getPoint2(); //on note le point pr�c�dent au point recherch�
                    cheminInverseNum.add(numLiaisonSelectionne.get(i));
                }
            }
        }
        cheminInverse.add(x); //on ajoute le dernier point (non ajout� par la boucle au dessus)
        chemin = new String[cheminInverse.size()]; //on cr�e la variable finale
        cheminNum = new int[cheminInverseNum.size()];


        for (int i = 0; i < cheminInverse.size(); i++) //on inverse toutes les lettres (fin au d�but, d�but � la fin)
            chemin[i] = cheminInverse.get(cheminInverse.size() - 1 - i);
        for (int i = 0; i < cheminInverseNum.size(); i++)
            cheminNum[i] = cheminInverseNum.get(cheminInverseNum.size() - 1 - i);
    }

    private static boolean testDejaFait(int plusPetit) {
        boolean dejaFait = false;
        if (pointsDejaFaits.contains(liaison.get(plusPetit).getPoint1())) { //si le point se trouve dans la liste des d�ja faits
            liaison.remove(plusPetit); //on le supprime de la liste
            liaisonNum.remove(plusPetit);
            dejaFait = true; //le point �tait d�ja fait
        }
        return dejaFait;
    }

    public static int[] getPathNums() {
        return cheminNum;
    }

    public static String[] getPath() {
        return chemin;
    }

    public static int getPathSize() {
        return chemin.length;
    }
}
