# SER - Labo 3 - JSON/KML

**Auteurs**: Mathieu Maillard, Yanick Thomann
**Date**: 28.05.2021



## Introduction

Le but de ce laboratoire est de créer un programme Java, permettant de convertir un fichier _geojson_ en _KML_. Le parsing du fichier geojson doit se faire avec les classe **json.simple** et l'écriture du fichier KML avec **JDOM2**. 



## Descriptif des classes

Nous utilisons dans ce programme 3 classes:

- **Country**: Cette classe permet de stocker toutes les informations relatives à un pays. Elle stocke notamment le nom, le nom ISO, le type de polygone utilisé pour représenter le pays, ainsi que les coordonnées du polygone ou le tableau de toutes les coordonnées d'un multipolygone.

- **GeoJsonParser**: Cette classe contient les méthodes pour parser le fichier geojson. Nous appelons un constructeur par défaut pour initialiser le tableau de pays. Ensuite, la méthode `parse()` est utilisée pour lire le fichier XML, créer les pays avec leurs informations, et populer le tableau `countries` avec tous les pays crées.
- **KMLWriter**: Cette classe est utilisée pour écrire le fichier KML, en s'aidant du tableau de pays `countries` de la classe **GeoJsonParser**. On appelle le constructeur par défaut pour créer l'objet, puis la méthode `write()` est utilisée pour créer le fichier KML.



## Problèmes connus

// TODO 



## Résultats

Une partie du résultat du parsing du fichier _geojson_ est affiché ci-dessous:

```
(ABW) Aruba
	- 26 coordinates
(AFG) Afghanistan
	- 1533 coordinates
(AGO) Angola
	- 14 coordinates
	- 1492 coordinates
	- 139 coordinates
(AIA) Anguilla
	- 24 coordinates
	- 4 coordinates
(ALB) Albania
	- 557 coordinates
(ALA) Aland
	- 12 coordinates
	- 17 coordinates
	- 15 coordinates
	- 32 coordinates
	- 9 coordinates
	- 15 coordinates
	- 24 coordinates
	- 21 coordinates
	- 14 coordinates
	- 10 coordinates
	- 40 coordinates
	- 20 coordinates
	- 13 coordinates
	- 19 coordinates
	- 200 coordinates
	- 11 coordinates
(AND) Andorra
	- 51 coordinates
(ARE) United Arab Emirates
	- 53 coordinates
	- 16 coordinates
	- 19 coordinates
	- 15 coordinates
	- 17 coordinates
	- 45 coordinates
	- 18 coordinates
	- 13 coordinates
	- 7 coordinates
	- 582 coordinates
(ARG) Argentina
	- 13 coordinates
	- 148 coordinates
	- 193 coordinates
	- 22 coordinates
	- 15 coordinates
	- 25 coordinates
	- 14 coordinates
	- 4271 coordinates
(ARM) Armenia
	- 13 coordinates
	- 404 coordinates
...
```



Ci-dessous, nous voyons le résultat obtenu lors de l'import du fichier KML dans Google Earth.

## Difficultés rencontrées





## Apprentissages





## Conclusion

