# 💡 Un système de gestion de la billetterie d'un réseau ferroviaire 

**Auteurs (trices) :** Illia VLASENKO - Van Trang DANG - William PLEYERS

## Contenus
- [1. Acteurs et cas d'utilisation](#1-acteurs-et-cas-dutilisation)
  - [1.1. Description détaillée des Acteurs](#11-description-detaillee-des-acteurs)
    - [1.1.1. Voyageur (Client)](#111-voyageur-client)
    - [1.1.2. Contrôleur (Unité de contrôle)](#112-controleur-unite-de-controle)
    - [1.1.3. Administrateur système](#113-administrateur-systeme)
    - [1.1.4. Serveur central (Système externe logique)](#114-serveur-central-systeme-externe-logique)
    - [1.1.5. Système de paiement simulé](#115-systeme-de-paiement-simule)
  - [1.2. Diagramme de Cas d’Utilisation Global](#12-diagramme-de-cas-dutilisation-global)
- [2. Scénarios d’utilisation](#2-scenarios-dutilisation)
- [3. Données nécessaires à la compréhension du système](#3-donnees-necessaires-a-la-comprehension-du-systeme)
- [4. Autres diagrammes](#4-autres-diagrammes)
- [5. Catalogue de questions / problèmes](#5-catalogue-de-questions--problemes)

<div style="page-break-after: always;"></div>

## 1. Acteurs et cas d'utilisation

Cette section présente les acteurs principaux du système, selon une approche orientée UML. Un acteur désigne toute entité - humaine ou logicielle - qui intéragit avec le système, influence son comportement ou utilise ses services. Chaque acteur est décrit selon son rôle, ses objectifs, ses interactions possibles et ses limites opérationnelles.

### 1.1. Description détaillée des Acteurs 
#### 1.1.1. Voyageur (Client)

Le voyageur représente l’acteur principal du système. Il s’agit de l’**utilisateur final** qui interagit directement avec l’interface de l’application pour rechercher un trajet, acheter un billet, consulter les titres qu’il possède et présenter un billet lors d’un contrôle. Son objectif est d’accéder rapidement à une solution de mobilité simple et dématérialisée, lui permettant d’obtenir et d’utiliser un billet numérique en toute autonomie.

Le voyageur initie la quasi-totalité des actions liées au cycle de vie du billet. Il peut **créer un compte**, **saisir des informations personnelles** nécessaires, **rechercher un trajet** entre deux villes du réseau fixe, **visualiser les services disponibles**, puis **effectuer un achat** à travers un processus de paiement simulé. Une fois le billet généré, il peut **le consulter** à tout moment et accéder à son code optique, qui servira de support lors d’un contrôle.

Ses droits sont limités à son propre espace utilisateur. Il ne peut ni modifier les services ferroviaires, ni consulter les données d’autres voyageurs. Ses interactions sont exclusivement orientées utilisateur, sans accès aux fonctionnalités d’administration ou aux outils internes du système. Enfin, il dépend entièrement du système de billetterie pour s’informer de la validité, de l’expiration ou de la validation de ses billets - ces informations lui sont transmises via des notifications.

#### 1.1.2. Contrôleur (Unité de contrôle)

Le contrôleur est l’acteur chargé de vérifier la validité des billets présentés par les voyageurs. Son interaction avec le système est fonctionnelle et opérationnelle : il n’achète pas de billet, mais utilise un terminal dédié capable de scanner les codes optiques, de consulter la validité d’un billet et d’enregistrer une validation en ligne ou en mode dégradé.

Le contrôleur a pour objectif principal de déterminer si un billet est authentique, valide pour le service en cours et non encore utilisé. **En mode connecté**, il interroge le serveur central pour obtenir la décision de validation globale. **En mode hors ligne**, il effectue un contrôle local basé sur un cache sécurisé et enregistre le résultat dans un journal temporaire, qui sera synchronisé lors du retour du réseau.

Contrairement au voyageur, le contrôleur possède des droits supplémentaires liés au cycle de validation : il peut **effectuer des vérifications**, **consulter certaines données de validation** (horodatage, duplications éventuelles), et **synchroniser son terminal**. Cependant, il n’a pas la capacité de modifier des données système, de créer des services ou d’accéder aux informations personnelles des voyageurs. Son rôle est strictement limité à l’authentification des billets et à l’assurance de la conformité du flux de contrôle.

#### 1.1.3. Administrateur système

L’administrateur système est responsable de la configuration et du bon fonctionnement global du système. Son rôle n’est pas opérationnel mais structurel : il définit les éléments statiques sur lesquels reposent les opérations quotidiennes, notamment la configuration du réseau ferroviaire (10 villes), la création des services de transport, la mise en place des tarifs, ainsi que la gestion de la base de clients.

L’administrateur est l’**unique** acteur possédant **des droits d’écriture** sur la structure interne du système. Il peut **ajouter, modifier ou désactiver des services**, **gérer les comptes utilisateurs** en cas d’erreur ou de fraude, et **surveiller la cohérence** globale de la base de données. Ses interactions sont moins fréquentes que celles des voyageurs ou des contrôleurs, mais elles sont essentielles pour garantir la stabilité du système.

Il ne participe pas au processus de validation des billets ni à l’achat de billets, mais il assure le maintien des règles de gestion, la mise à jour des configurations et la supervision des données critiques. De ce fait, cet acteur représente un pivot organisationnel plutôt qu’un utilisateur opérationnel.

#### 1.1.4. Serveur central (Système externe logique)

Dans le cadre du système de billetterie, le serveur central est considéré comme un composant logique interne assurant le rôle d’autorité centrale de validation et de gestion des règles métier. Bien qu’il ne soit pas modélisé comme un acteur dans le diagramme de cas d’utilisation, il joue un rôle essentiel dans le traitement des requêtes et la cohérence globale du système.

Le serveur central traite les requêtes des voyageurs (achats, consultation), et surtout celles des contrôleurs lors des validations. Il décide si un billet est valide, expiré, déjà utilisé ou frauduleux. Lors d’une synchronisation après un contrôle hors ligne, il résout les éventuels conflits en appliquant l’horodatage des validations.

De plus, il gère la génération des billets, l’unicité des identifiants, l’intégrité des données et la cohérence du système. Il assiste les autres acteurs sans être un utilisateur humain.

#### 1.1.5. Système de paiement simulé (Acteur optionnel)

Bien que non essentiel en production réelle, un système de paiement simulé est considéré comme un acteur externe dans le cadre du projet. Il représente le composant chargé de renvoyer au système un accord de paiement fictif, permettant l’émission du billet.

Ce système est minimaliste : il ne vérifie pas de carte bancaire, ne communique pas avec une banque, mais fournit une réponse logique (“paiement accepté” ou “échec”) afin de tester le comportement du système dans un contexte pseudo-réel. Il permet donc d’isoler le processus d’émission sans nécessiter de prestataire externe.

### 1.2. Diagramme de Cas d’Utilisation Global 

![Diagramme de cas d'utilisation](images/use_case.png)

--- 

## 2. Scénarios d’utilisation (20 pages scnenarios)


## 1. Purchase Ticket - Sequence Diagram

![Purchase Ticket](images/2_1.svg)

## 2. Online Validation - Sequence Diagram
![Online Validation](images/2_2.svg)
## 3. Offline Validation - Sequence Diagram
![Offline Validation](images/2_3.svg)
## 4. Synchronisation - Sequence Diagram
![Synchronisation](images/2_4.svg)
## 5. Automatic Expiration - Sequence Diagram
![Automatic Expiration](images/2_5.svg)
## 6. Double Scan - Sequence Diagram
![Double Scan](images/2_6.svg)
## 7. Network Unavailable - Sequence Diagram
![Network Unavailable](images/2_7.svg)

---

## 3. Données nécessaires à la compréhension du système (5 à 10 pages)


## Class Diagram

![Class Diagram](images/3.png)

## Hypothèses sur les données

    Pour simplifier le projet :

        - Les paiements sont simulés.

        - L’identité des utilisateurs est validée sans FranceConnect.

        - Le réseau ferroviaire est prédéfini (pas de modification en temps réel).

        - La durée “heure d’arrivée + 10 minutes” est utilisée pour l’expiration.

---

## 4. Autres diagrammes (0 à 10 pages)

---

## 5. Catalogue de questions / problèmes (3 pages)
