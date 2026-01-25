Sujet : tou-tou (garder pour le nom d'appli)

Idées : 
    - web service
    - postgre sql pour la base de données
    - outis pour gestion en temmps réel
    - interface utilisateur intuitive
    - système de notifications pour les tickets, updates
    - gestion des codes qr (penser à la gestion de validation multiple)
    - authentification
    - trouver un trajet entre deux points A et B (implémenter les maps pour ça?) 


Qui s'occupe de quoi :
    - William : interface
    - Lia : backend
    - Van trang : base de données, web service


Cdc: 
# 💡 Un système de gestion de la billetterie d'un réseau ferroviaire 

## 1. Contexte, Glossaire (Vocabulaire du domaine c'est quoi wwebservice, controller par ex)

### 1.1. Contexte général du projet
Le présent projet s'inscrit dans la conception d'un système de billetterie numérique permettant la gestion complète de titres de transport pour un réseau ferroviaire simplifié.

Dans un contexte où la dématérialisation des services publiques et la sécurisation des intéractions client - systèmes deviennent des enjeux majeurs, il est nécessaire de disposer d'un service fiable, cohérent et vérifiable permettant:

    - la recherche, l'achat et l'émission de billets électroniques
    - la gestion d'un réseau fixe de services de transport
    - la vérification locale des titres par une unité de contrôle
    - l'identification d'un client via un code optique
    - la réduction des risques de fraude

Le système vise principalement un environnement pédagogique et expérimental, mais doit refléter les contraintes essentielles d’un système réel : cohérence fonctionnelle, intégrité des données, traçabilité des actions et robustesse face aux usages courants.

Les utilisateurs cibles sont :

    - les clients achetant des billets,
    - les agents de contrôle vérifiant la validité des titres de transport,
    - l’administrateur système, responsable de la configuration statique du réseau, des services et de la base client.

Le produit final doit garantir un fonctionnement fiable, une communication claire entre composants et une utilisation conforme au périmètre défini.

### 1.2. Contexte métier

Le domaine de la billetterie numérique implique une série de notions métiers centrales :

    - **Un réseau de transport**, composé d’au moins dix villes reliées par des services ferroviaires planifiés. Ce réseau est déterminé à l’avance et ne peut être modifié dynamiquement dans le cadre du projet.

    - **Des clients enregistrés**, disposant d’identifiants permettant l’émission de titres personnalisés. Ces clients constituent un ensemble fixe au démarrage du système.

    - **Des services de transport** définis par un train, une date, une heure et un trajet allant d’un point A à un point B. Chaque service correspond à un événement unique et constitue une unité facturable.

    - **Une mécanique de tarification** rudimentaire, reflétant le coût d’un service sans nécessiter l’intégration de solutions de paiement réelles.

    - **L’émission de titres client-dépendants**, comportant **un code optique** servant d’identifiant unique du billet. Ce code sert de support à la vérification locale et doit garantir la non-répudiation du titre.

    - **Un contrôle localisé**, assuré par une application spécifique capable de lire le code optique et d’interroger le serveur pour déterminer si le billet présenté est valide pour le service en cours.

Ce contexte délimite clairement les responsabilités du système, les interactions essentielles entre les acteurs et les flux d’information critiques permettant la validation correcte d’un titre.

### 1.3. Vocabulaire spécifique (Glossaire métier)

---

## 2. Objectifs
    - Fonctionnel (à qui, à quelles fins)
    - Non-Fonctionnel (Sécurité - par ex doit éviter les fraudes, simplicité d'utilisation )

---

## 3. Contraintes 

### 3.1. Contraintes techniques 

Le système devra reposer sur une architecture client–serveur : 
        
    - C'est une architecture classique pour les services qui disposent d'interactions avec des données. Le client envoie une requête au serveur qui est éventuellement traitée.
        
    Dans notre cas, les exemples de telles interactions sont la créations du compte, la validation des billets ou l'achat d'un ticket en ligne.

Le backend devra être implémenté sous forme de service web (API REST) : 
        
    - Une contrainte nécessaire est de faire les services fonctionner et de les faire communiquer entre eux. Pour cela, on implementera une API pour nos services.

Les données devront être stockées dans une base de données relationnelle (PostgreSQL) :
        
    - Comme les données doivent être stockées quelque part, on va implémenter une base de données qui sauvegardera les données des utilisateurs, les billets achetés et les trajets.
        
    Chaque fois qu'on aura besoin d'accéder aux données, on enverra une requête à cette base, qui nous transmettra éventuellement les données demandées. 

La validation définitive d’un billet nécessitera un accès au serveur central :
        
    - Le système de validation des billets est l'un des aspects les plus durs de ce projet. Il faut penser à beaucoup de choses, comme la fraude ou les billets simplement invalides.
        
    Cela n'est possible qu'avec une connexion au réseau stable, un requis non négligeable pour acceder à la base de données afin de valider les informations avec la meilleure précision.

Le système devra prévoir un mode dégradé en cas d’indisponibilité du réseau, limité à la lecture du billet et à une pré-validation locale : 
        
    - Il faut penser au fait que la validation définitive n'est possible que avec la connexion au réseau, ce qui n'est pas toujours le cas (dans le tunnels, sur des stations loin de la civilisation). Pour résoudre ce probléme, une proposition consiste à implementer une validation "partielle" sans réseau, qui permettra valider au moins l'integralité du billet, la cohérence crypto et de marquer que le ticket a été validé localement. Cette contrainte est nécessaire et est utilisée par plusieurs systèmes comme SNCF.

### 3.2. Contraintes de sécurité

Les billets devront être protégés contre la falsification :
        
    - Il faut toujours penser à la fraude. On propose d'introduire une solution classique assez simple, qui consiste à générer un code QR unique pour chaque billet existant. Cette solution nous permettra d'assurer l'unicité des billets, tandis que la vérification côté serveur permet de détécter les tentatives de fraude. 
    
***Comment?*** 

    - Chaque billet sera associé à un compte utilisateur qui l'a acheté. Il sera possible d'en obtenir un uniquement après un paiement succesif. Les tickets sont gardés sur le serveur, donc si le ticket n'y est pas présent ou s'il ne correspond pas au compte depuis duquel il a été scanné, il y a alors une possibilité de fraude. C'est aussi un aspect discutable. On n'a pas envie de forcer chaque utilisateur à télécharger l'application et à passer la vérification (ce qui est éffectivement la meilleure solution possible), mais de pouvoir retrouver le ticket dans la boîte aux mails. Dans ce projet, pour simplifier un peu la vie, nous allons rester avec la prémiere idée, qui consiste à associer les billets au comptes physiques validés.

    - Chaque billet devra être identifié de manière unique :Contrainte d'unicité assez typique qui consiste à faire de sorte que chaque identifiant de tickets soit unique. C'est assez réalisable et n'est pas le piége le plus difficile de ce projet.

    - Un même billet ne devra pas pouvoir être validé plusieurs fois au niveau global :Comme déjà précisé, il y aura 2 niveaux de validation (locale et globale). La validation globale ne pourra se faire qu'une seule fois. Qu'est-ce-qu'on va faire si le billet sera validé la deuxieme fois? Après la validation, on le marque valide et on garde les informations suivantes : par qui il a été validé et quand. Lors les vérifications suivantes, ces informations seront affichées pour les controlleurs. Il faut également penser au cas où le ticket a été bien validé, mais où l'utilisateur souhaite l'utiliser une deuxième fois pour un autre trajet. Comment on fait? On peut résoudre ce probléme grâce à une validité globale du ticket. Pour chaque trajet on aura une heure approximative d'arrivée. Chaque minute, lorsque le serveur actualise les données, il marquera invalides tous les billets dont l'heure d'arrivée + 10 minutes (temps approximative pour sortir du quai et où pendant lequel une validation peut encore avoir lieu) est déjà dépassée. Ce n'est pas la solutions la plus sécurisé, mais cela permet quand-même de réduire fortement les tentatives de fraude.

    - Les données personnelles des utilisateurs ne devront pas apparaître dans les codes QR :Cette contrainte est trés typique et demandée par nombreux standards de sécurité au niveau gouvernement. Aucune information personnelle ne sera stockée dans les codes générés, uniquement l'identifiant de l'utilisateur et l'identifiant du billet.

    - L’authentification sera requise pour toute opération sensible (achat, validation, administration) : Cette contrainte consiste à vérifier les droits nécessaires à gérer le système ou des operations sur un billet.

### 3.3. Contraintes économiques

    - Le projet devra utiliser exclusivement des technologies open-source : Évidemment, on n'a pas accès aux technologies privées des entreprises. On va se contenter avec des outils en accés libre, par exemple la génération des codes QR de Google.

    - L’infrastructure devra être déployable sur des ressources limitées : Cette contrainte implique que le système puisse fonctionner sur une infrastructure serveur légère, disposant de ressources matérielles limitées (CPU, mémoire, stockage).

    - L’architecture proposée repose sur une API REST monolithique et une base de données relationnelle unique (PostgreSQL), afin de limiter la complexité du déploiement et la consommation de ressources. Aucun service externe coûteux ou fortement consommateur de ressources ne sera requis. Cette approche permet un déploiement sur un serveur mutualisé ou une machine de capacité modeste, tout en assurant les fonctionnalités essentielles du système.
      
    - Les coûts liés aux services externes devront être évités ou simulés : Il est évident que, pour un projet étudiant à présenter, aucun paiement réel ne sera éffectué. Tous les paiements seront simulés, ainsi que la validation des comptes 
    (pas de connexion avec FranceConnect). Ça va quand-même rester une possibilité si on va penser à vendre notre produit.

### 3.4. Contraintes opérationnelles

    - Le système devra fonctionner malgré une connexion réseau instable :Cette contrainte est déjà décrite précédemment, avec l'introduction d'une validation locale si le réseau n'est pas accessible. 

    - En cas de perte de connexion, les opérations critiques devront être reportées : Un exemple est la validation locale, qui peut être reportée jusqu'au moment où la connexion est rétablie. Ça peut être implémentée sous forme de requêtes mises en attente, envoyées automatiquement lorsque la connexion sera établie.

    - La synchronisation avec le serveur devra permettre la résolution de conflits liés aux validations multiples : Cette contrainte a également été décrite précédemment, ainsi que les solutions possibles.

## 4. Exigences fonctionnelles

4.1 Fonctionnalités principales

    1) Recherche de trajet

        - Le système devra permettre à un utilisateur de rechercher un trajet entre deux points A et B.

        - Le calcul du trajet reposera sur un réseau ferroviaire prédéfini.

        - L’intégration de services cartographiques externes est optionnelle mais trés souhaitable à introduire.

    2) Achat et émission de billet

        - Le système devra permettre l’achat d’un billet pour un trajet sélectionné (s'il y a des places disponibles).

        - Le processus de paiement sera simulé.

        - À l’issue de l’achat, un billet électronique unique devra être émis et sauvegardé dans le compte de l'utilisateur.

    3) Génération et gestion des codes QR

        - Chaque billet devra être associé à un code QR unique.

        - Le code QR devra permettre l’identification du billet par le système.

        - Le contenu du code QR ne devra pas permettre l’accès direct aux données personnelles.

        - Le système devra empêcher la validation multiple d’un même billet au niveau du serveur si le billet est déjà validé, ou prévenir le contrôleur que ce billet est déjà validé.
          
          Ici on fait une distinction entre être validé et être valide : 
    
            * un billet valide est un billet qui a une vraie validité au niveau du paiement, de la date, du temps et du trajet.
    
            * un billet validé est un billet VALIDE qui a été validé par un controlleur.

    4) Validation des billets

        - La validation définitive d’un billet nécessitera une communication avec le serveur.
    
        - En cas d’indisponibilité du réseau, le système devra permettre :

            * la lecture du code QR;

            * une pré-validation locale temporaire.

        - Toute validation locale devra être synchronisée ultérieurement avec le serveur.

        - En cas de conflit, la première validation enregistrée par le serveur fera foi.


    5) Notifications

        - Le système devra notifier l’utilisateur :

            * de l’émission d’un billet;

            * de sa validation;

            * de son annulation ou expiration.

4.2 Scénarios d’utilisation (extraits corrigés)
    
    Scénario "Contrôle d’un billet" :

        1) Le contrôleur scanne le code QR du billet.

        2) Le système tente une validation en ligne.

        3.a) Si le réseau est disponible, la validation est effectuée sur le serveur.

        3.b) Si le réseau est indisponible, une pré-validation locale est enregistrée.

        4) Une synchronisation ultérieure permet la validation définitive ou la détection d’un conflit.

4.3 Comportements & protocole (abstrait)

    - Les échanges entre clients et serveur s’effectuent via HTTP.

    - Les données sont transmises au format JSON.

    - Le serveur agit comme autorité centrale de validation.

    - Les terminaux de contrôle ne disposent d’aucune autorité de validation définitive, celle-ci est assurée par le système principal.

---

## 5. Hypothèses & Limitations 
    - Simplifications acceptées (par ex: paiements simulés)

---


## 6. Critères de validation

Les critères de validation définissent l’ensemble des conditions permettant d’évaluer objectivement la conformité du système final aux exigences formulées dans le Cahier de Charges. Ils constituent la base permettant au client, aux utilisateurs et à l’équipe de développement de juger si le produit livré répond correctement aux besoins fonctionnels et non fonctionnels.

### 6.1. Validation des exigences fonctionnelles

Le système sera considéré conforme si les opérations suivantes peuvent être réalisées de manière correcte, cohérente et reproductible :

    - Gestion d’un réseau fixe d’au moins dix villes.
    - Gestion d’un ensemble de clients prédéfinis, correctement identifiés.
    - Achat et émission d’un billet unique, associé à un service spécifique et à un client déterminé.
    - Génération d’un code optique unique et décodable pour chaque billet.
    - Authentification localisée d’un billet à partir de l’unité de contrôle.
    - Gestion explicite des cas d’erreur (billet inexistant, service incorrect, duplication, format invalide).

### 6.2. Validation des critères non fonctionnels

Le système devra satisfaire les exigences suivantes :

    - Fiabilité des processus critiques (émission, authentification, communication entre composants).
    - Performance raisonnable, notamment pour les réponses d’authentification.
    - Sécurité des données clients et des liens client–billet.
    - Intégrité des données, sans mise à jour partielle ou incohérente.
    - Simplicité d’usage, notamment pour l’unité de contrôle.

### 6.3. Critères d’acceptation par les parties prenantes

Le système est jugé acceptable lorsque :

    - Les clients peuvent acheter un billet et obtenir un code optique utilisable sans assistance.
    - Les agents de contrôle peuvent vérifier un billet rapidement et sans ambiguïté.
    - L’administrateur peut gérer la configuration initiale sans intervention technique complexe.
    - Tous les scénarios d’usage définis sont exécutables de bout en bout.

### 6.4. Validation en conditions de connectivité limitée (usage à bord du train)

Dans un contexte ferroviaire réel, l’unité de contrôle peut être utilisée dans un environnement à connectivité faible, instable ou inexistante. Le système devra donc respecter les critères suivants :

    - Tolérance à l’absence de réseau : En cas de non-disponibilité de Wi-Fi ou de données mobiles, l’unité de contrôle doit afficher un message explicite indiquant que l’authentification en temps réel est impossible, sans provoquer d’erreur interne ni de blocage.
    - Comportement fail-safe : En absence de connexion, l’application ne doit jamais valider un billet, ni produire de décision ambiguë. Elle doit rester neutre pour éviter toute violation des règles de sécurité.
    - Non-altération des données locales : L’absence de réseau ne doit entraîner aucune corruption, perte ou duplication de données stockées localement.
    - Reprise automatique des opérations : Dès que la connectivité est rétablie, l’application doit retrouver un fonctionnement normal sans nécessiter de redémarrage manuel.
    - Cohérence après synchronisation : Le rétablissement du réseau ne doit pas créer d’état incohérent entre l’unité de contrôle et le serveur central.

### 6.5. Conditions de conformité finale

Le produit final est conforme lorsque :

    - Toutes les exigences du Cahier de Charges sont satisfaites.
    - Aucun comportement contradictoire ou non spécifié ne subsiste dans les fonctions principales.
    - Les scénarios fonctionnels sont exécutables sans correction manuelle.
    - Les éventuels écarts sont documentés, justifiés et validés par le client.