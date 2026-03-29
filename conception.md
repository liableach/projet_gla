# 💡 Un système de gestion de la billetterie d'un réseau ferroviaire 

**Auteurs (trices) :** Illia VLASENKO - Van Trang DANG - William PLEYERS


## 1. Introduction 
Ce document présente la conception détaillée d’un système de gestion de la billetterie d’un réseau ferroviaire. Il s’inscrit dans la continuité de l’analyse fonctionnelle réalisée en phase D1, dont l’objectif principal était d’identifier les besoins métier, les contraintes du domaine ainsi que les principaux scénarios d’utilisation du système.

Contrairement à cette première phase d’analyse, la présente phase de conception (D2) est orientée vers la technologie cible retenue pour l’implémentation. Son objectif n’est plus seulement de décrire ce que le système doit faire, mais également de préciser comment les composants du système seront organisés, structurés et amenés à collaborer afin de répondre aux exigences identifiées précédemment.

Le système étudié vise à fournir une solution de billetterie numérique permettant à des voyageurs de rechercher un trajet, d’acheter un billet électronique, de consulter ce billet sous forme dématérialisée et de le présenter lors d’un contrôle. En parallèle, il doit permettre aux agents de contrôle de vérifier la validité des billets, y compris dans des situations de connectivité réseau limitée, grâce à un mécanisme de contrôle local et de synchronisation différée avec le serveur central.

Cette phase de conception a donc pour ambition de rendre explicites :

    - les acteurs d’implémentation du système,
    - la structure globale de l’architecture logicielle,
    - les interfaces utilisateur principales,
    - les modèles statiques et dynamiques du système,
    - les scénarios détaillés fondés sur des échanges de données concrets, 
    - les objets critiques manipulés par le système,

Le présent rapport adopte ainsi une approche progressive, allant de la vue générale de la solution vers des éléments de conception plus détaillés, notamment les diagrammes UML, les maquettes d’interfaces, les scénarios d’exécution et les représentations des cycles de vie des objets critiques tels que les billets et les validations.

Enfin, ce document constitue une base de référence pour la future phase d’implémentation. Il vise à garantir la cohérence entre les choix de conception, les contraintes techniques du projet et les besoins fonctionnels identifiés lors de la phase précédente.

## 2. Technologie cible et principes de conception

## 3. Acteurs d’implémentation du système

(5 pp UML plus descr.)

## 4. Interface utilisateur – GUI Mockups

## 5. Architecture générale du système
![Diagramme](images/conception/diagram_archi.svg)
![Diagramme](images/conception/diagram_deployment.svg)
![Diagramme](images/conception/diagram_activity_purchase.svg)
![Diagramme](images/conception/diagram_activity_validation.svg)

## 6. Modèle statique - Class Diagrams
 ![Diagramme](images/conception/diagram_classes.svg)

## 7. Scénarios d’utilisation détaillés 

# Diagramme Achat d'un Ticket
![Diagramme](images/conception/diagram_seq_buy.svg)
# Diagramme Validation d'un Ticket
![Diagramme](images/conception/diagram_seq_validation.svg)
# Diagramme Gestion Offline
![Diagramme](images/conception/diagram_seq_offline.svg)
# Diagramme Synchronisation après Offline
![Diagramme](images/conception/diagram_seq_sync.svg)
# Diagramme Expiration d'un Ticket
![Diagramme](images/conception/diagram_seq_expiration.svg)
# Diagramme Gestion de Double Scan
![Diagramme](images/conception/diagram_seq_double-scan.svg)
# Diagramme Gestion de Connexion
![Diagramme](images/conception/diagram_seq_manage-connection.svg)

## 8. Object diagrams pour données critiques
# Diagramme Objet Gestion de Validation 
![Diagramme](images/conception/diagram_object_validation.svg)
# Diagramme Objet Pré-validation
![Diagramme](images/conception/diagram_object_pre-validation.svg)
# Diagramme Objet Double Vérification
![Diagramme](images/conception/diagram_object_double-scan.svg)
