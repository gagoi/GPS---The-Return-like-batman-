package fr.gagoi.gps.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Map {

	private Link[] links; // Tableau contenant toutes les liaisons.
	private GPS gps; // Instance du programme, permet d'utiliser le logger.

	public Map(String name, GPS gps) {
		this.gps = gps; // Permet de loger en dehors du constructeur.
		JSONParser parser = new JSONParser(); // Creation d'un parser JSON.
		try {
			JSONObject obj = (JSONObject) parser.parse(new FileReader("c:\\Users\\Gagoi\\Desktop\\" + name + ".json")); // Creation d'un objet JSON, qui est tout le contenu du fichier de map.

			int numberOfLinks = Integer.parseInt((String) obj.get("numberOfLinks")); // Recuperation du nombre de liaisons dans le fichier.
			links = new Link[numberOfLinks * 2]; // Creation d'un tableau contenant toutes les liaisons. La multiplication par 2 permet de mettre des liaisons dans chaque sens (A --> B && B --> A).

			JSONArray jsonLinks = (JSONArray) obj.get("links"); // Tableau JSON contenant les liaisons de la map (un seul sens pour le moment).
			for (int i = 0; i < numberOfLinks; i++) { // On parcourt le tableau JSON.
				JSONObject jsonLink = (JSONObject) jsonLinks.get(i);// Creation d'un objet JSON pour la liaison "i".
				links[i] = new Link((String) jsonLink.get("startPoint"), (String) jsonLink.get("endPoint"), Integer.parseInt((String) jsonLink.get("weight"))); // Ajout au tableau des liaisons une nouvelle liaison (A --> B).
				links[i + numberOfLinks] = new Link((String) jsonLink.get("endPoint"), (String) jsonLink.get("startPoint"), Integer.parseInt((String) jsonLink.get("weight"))); // Ajout également la liaison opposée (B --> A).

				gps.log("MAP", links[i].toString()); //Utilisation du logger pour avoir les informations de debug, ici la liaison "i" de A --> B.
				gps.log("MAP", links[i + numberOfLinks].toString()); //Utilisation du logger pour avoir les informations de debug, ici la liaison "i+numberOfConnection", c'est a dire "i" de B --> A.
			}
		} catch (IOException e) {
			gps.log("ERROR", "Impossible to load the file."); // Utilisaiton du logger pour afficher l'erreur.
			e.printStackTrace();
		} catch (ParseException e) {
			gps.log("ERROR", "Impossible to parse the file."); // Utilisaiton du logger pour afficher l'erreur.
			e.printStackTrace();
		}

		travel("MN0", "AB2");
	}

	public void travel(String startPoint, String endPoint) {
		String lastPoint = startPoint;
		int weight = 0;
		do {
			getNextLink(startPoint, weight);
		} while (lastPoint.equals(endPoint));
	}
	private ArrayList<Integer> getConnectionsIndex(String point) {
		ArrayList<Integer> connections = new ArrayList<Integer>(); // Creation d'une list contenant les connections valides.
		gps.log("CONNECTIONS", "Start getting number of connections for the point : " + point); // Utilisation du logger pour afficher l'introduction de la fonction.
		for (int i = 0; i < links.length; i++) { //On parcourt le tableau des liaisons.
			if (links[i].getStartPoint().equals(point)) { //Si le point de départ d'une liaison est notre "point" : 
				connections.add(i); // Ajout de la connection a la liste des connections valides.
				gps.log("CONNECTIONS", "\t- connected to the point : " + links[i].getEndPoint() + ", link number : " + i + "."); // Utilisation du logger pour afficher chaque point connecte, ainsi que l'index de la liaison dans le tableau.
			}
		}
		gps.log("CONNECTIONS", "Operation has ended. There are " + connections.size() + " connection avaible with this point."); // Utilisation du logger pour afficher la conclusion de la focntion.
		return connections; // Renvoie les connections existantes.
	}

	private Link getNextLink(String point, int actualWeight) {
		ArrayList<Integer> linksList = getConnectionsIndex(point); // Recuperation de la liste des liaisons en relation avec "point".

		int indexShorter = linksList.get(0); // Declaration de la premiere liaison du tableau en tant que plus courte.
		int indexShorterWeight = actualWeight + links[indexShorter].getWeight(); // Poids de la plus courte des liaisons calculé jusqu'a maintenant.

		for (int i = 1; i < linksList.size(); i++) { //On parcourt la liste des liaisons en relations avec "point".
			int testedIndexShorterWeight = actualWeight + links[i].getWeight(); // Poids total depuis le debut du trajet jusqu'au point de fin de la liaison d'index "i".
			int minWeight = Integer.min(indexShorterWeight, testedIndexShorterWeight); // Comparaison du poids le plus petit avec le poids de la liaisons actuellement calcule "links[i]". On conserve le plus court.
			if (minWeight == testedIndexShorterWeight) { // Si la liaison que l'on calcul est plus courte/legere : 
				indexShorter = i; // on change l'id de la liaison la plus courte/legere,
				indexShorterWeight = actualWeight + links[indexShorter].getWeight(); // et on recalcule le poids le plus faible, pour les futurs comparaisons.
			}
		}

		gps.log("LINK", "The choosen link is the one with id : " + indexShorter + ".");
		return links[indexShorter];
	}

}
