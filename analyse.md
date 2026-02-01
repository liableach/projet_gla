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

1) Acheter un billet

    * Objectif : Permettre à un utilisateur d’acheter un billet électronique pour un trajet donné.

    * Acteurs : 
        
        - Utilisateur

        - Système de billetterie

        - Serveur central (backend)

    * Préconditions :

        - L’utilisateur est authentifié.

        - Le réseau est disponible.

        - Le trajet demandé existe dans le système.

    * Déroulement :

        - L’utilisateur recherche un trajet entre A et B.

        - Le système affiche les trajets disponibles.

        - L’utilisateur sélectionne un trajet.

        - Le système vérifie la disponibilité des places.

        - L’utilisateur confirme l’achat.

        - Le paiement est simulé.

        - Le serveur génère un billet unique et un QR code associé.

        - Le billet est enregistré dans la base de données et dans le compte de l’utilisateur.

        - Le système notifie l’utilisateur de l’émission du billet.

    * Variantes : 

        - Pas de places disponibles → le système refuse l’achat et propose un autre horaire.

        - Erreur serveur → l’achat est annulé et l’utilisateur est notifié.

--- 

2) Valider un billet (online)
    
    * Objectif : Vérifier et enregistrer officiellement la validité d’un billet via le serveur.

    * Acteurs : 
            
        - Contrôleur

        - Système de validation

        - Serveur central

    * Préconditions :

        - Le contrôleur dispose d’un terminal de contrôle fonctionnel.

        - Le billet possède un QR code valide.

        - Le réseau est disponible.

    * Déroulement nominal :

        - Le contrôleur scanne le QR code du billet.

        - Le terminal envoie l’identifiant du billet au serveur.

        - Le serveur vérifie :

            a) l’existence du billet,

            b) sa validité (paiement, date, trajet),

            c) s’il n’a pas déjà été validé.

        - Le serveur marque le billet comme validé (horodatage + identifiant du contrôleur).

        - Le terminal affiche “Billet validé”.

    * Variantes / erreurs :

        - Billet invalide → refus de validation.

        - Billet déjà validé → alerte au contrôleur.

        - Erreur serveur → message d’erreur.

--- 

3) Valider un billet (offline) 

    * Objectif : Permettre une validation minimale lorsqu’il n’y a pas de réseau.

    * Acteurs :

        - Contrôleur

        - Terminal de contrôle (mode hors ligne)

    * Préconditions :

        - Le terminal peut lire le QR code.

        - Le réseau est indisponible.

    * Déroulement :

        - Le contrôleur scanne le QR code.

        - Le terminal vérifie localement :

            a) la structure du QR code,

            b) que le billet n’est pas manifestement corrompu.

        - Le billet est marqué pré-validé localement sur le terminal.

        - Le contrôleur est informé que la validation est temporaire.

    * Limites :

        - Cette validation n’est pas définitive.

        - Elle devra être synchronisée plus tard avec le serveur.

--- 

4) Synchroniser après offline

    * Objectif : Transmettre au serveur les validations réalisées hors ligne.

    * Acteurs :

        - Terminal de contrôle

        - Serveur central

    * Préconditions :

        - Le terminal retrouve une connexion réseau.

        - Il contient des validations locales en attente.

    * Déroulement :

        - Le terminal détecte le retour du réseau.

        - Il envoie au serveur toutes les validations locales en attente.

        - Pour chaque billet, le serveur :

            a) vérifie s’il est valide,

            b) vérifie s’il a déjà été validé entre-temps.

        - Si aucune validation antérieure n’existe → la validation est confirmée.

        - Si un conflit existe → la première validation enregistrée par le serveur fait foi.

        - Le terminal met à jour son état local.

    * Cas de conflit :

        - Si le billet a été validé entre-temps par un autre contrôleur → la validation locale est rejetée.

--- 

5) Expiration d’un billet
    
    * Objectif : Rendre automatiquement invalides les billets dont la période d’usage est dépassée.

    * Acteurs : 
        
        - Serveur central

        - Système de gestion des billets

    * Préconditions : 
        
        - Les billets existent dans la base des données.

    * Déroulement : 

        - Chaque minute, le serveur vérifie les billets validés.

        - Pour chaque billet, il calcule :

            a) heure d’arrivée prévue + 10 minutes -> si ce délai est dépassé, le billet est marqué expiré.

            b) L’utilisateur est notifié de l’expiration.

    * Post-conditions : 

        - Un billet expiré ne peut plus être validé.

        - Il reste visible dans l’historique de l’utilisateur.

--- 

6) Gestion d’erreur (double scan)

    * Objectif : Empêcher la validation multiple d’un même billet.

    * Acteurs :

        - Contrôleur

        - Serveur central

    * Déroulement :

        - Le contrôleur scanne un billet déjà validé.

        - Le serveur détecte que le billet est déjà marqué comme validé.

        - Le terminal affiche “Billet déjà validé” avec : heure + contrôleur ayant validé.

    * Conséquences :

        - Le billet ne peut pas être revalidé.

        - Le contrôleur peut décider d’un contrôle manuel si nécessaire.

---

7) Gestion d'erreur (perte réseau)

    * Objectif : Assurer un fonctionnement minimal du système malgré une coupure réseau.

    * Acteurs :

        - Contrôleur

        - Terminal de contrôle

    * Déroulement : 

        - Le contrôleur tente une validation en ligne.

        - Le terminal détecte l’absence de réseau.

        - Le système bascule automatiquement en mode dégradé.

        - Une pré-validation locale est enregistrée.

        - Le terminal informe le contrôleur que la validation sera confirmée ultérieurement.

---

## 3. Données nécessaires à la compréhension du système (5 à 10 pages)


1) Vue d’ensemble des données du système

    Le système de billetterie repose sur un ensemble de données structurées permettant :

        - l’identification des utilisateurs et des rôles,

        - la description du réseau ferroviaire,

        - la gestion des billets électroniques,

        - la traçabilité des validations,

        - la synchronisation entre terminaux et serveur.

    Ces données sont principalement stockées dans une base de données relationnelle, tandis que certaines informations temporaires peuvent 
    être conservées localement sur les terminaux de contrôle en mode hors ligne.

---

2)  Données relatives aux acteurs 

    * Utilisateur : 

        - Données principales :

            a) user_id : identifiant unique de l’utilisateur

            b) nom

            c) prénom

            d) email

            e) mdp

            f) rôle

        - Rôle dans le système :

            a) Achat de billets

            b) Consultation de l’historique

            c) Réception de notifications

    * Contrôleur :

        - Données spécifiques :

            a) controller_id

            b) user_id

            c) numéro_matricule

            d) terminal_associe

        - Rôle dans le système :

            a) Scanner des QR codes

            b) Valider des billets

            c) Opérer en mode en ligne et hors ligne

    * Administrateur :

        - Données spécifiques :

            a) admin_id

            b) user_id

        - Rôle dans le système :

            a) Gestion du système

            b) Supervision des validations

            c) Gestion des trajets et incidents

---

3) Données relatives au réseau ferroviaire

    * Station :

        - station_id

        - nom_station

        - ville

    * Trajet : 

        - trajet_id

        - station_depart

        - station_arrivee

        - heure_depart

        - heure_arrivee_estimee

            - capacite_maximale

---

4) Données relatives aux billets

    * Billet : 

        - ticket_id (UUID unique)

        - user_id (propriétaire du billet)

        - trajet_id

        - date_achat

        - statut_paiement

        - statut_validite (valide / expiré / annulé)

        - qr_code_data

    * Remarque :

        Le QR code ne contient pas de données personnelles sensibles. Il contient uniquement un identifiant permettant au serveur de 
        retrouver le billet.

---

5) Données relatives à la validation des billets

    * Validation globale (serveur) :

        - validation_id

        - ticket_id

        - controller_id

        - timestamp_validation

        - statut (validé / refusé / en conflit)

    * Pré-validation locale (hors ligne) :

        - ticket_id

        - timestamp_scan

        - terminal_id

        - statut_local = "pré-validé"

---

6) Données pour la synchronisation

    * Lorsqu’un terminal retrouve une connexion réseau, il envoie :

        - la liste des billets pré-validés localement,

        - l’horodatage de chaque scan,

        - l’identifiant du terminal et du contrôleur.

    * Le serveur compare alors :

        - si le billet est toujours valide,

        - s’il a déjà été validé par un autre contrôleur.

---

7) Données de sécurité et anti-fraude

    * Pour limiter la fraude, le système s’appuie sur :

        - Un identifiant unique de billet (UUID)

        - Un QR code unique par billet

        - Une vérification systématique côté serveur

        - Un horodatage des validations

        - Une expiration automatique des billets après usage

---

8) Données de notification

    * Le système doit conserver et générer des événements tels que :

        - notification_id

        - user_id

        - type_notification (émission, validation, expiration)

        - message

        - timestamp

---

9) Hypothèses sur les données

    Pour simplifier le projet :

        - Les paiements sont simulés.

        - L’identité des utilisateurs est validée sans FranceConnect.

        - Le réseau ferroviaire est prédéfini (pas de modification en temps réel).

        - La durée “heure d’arrivée + 10 minutes” est utilisée pour l’expiration.

---

## 4. Autres diagrammes (0 à 10 pages)

---

## 5. Catalogue de questions / problèmes (3 pages)
