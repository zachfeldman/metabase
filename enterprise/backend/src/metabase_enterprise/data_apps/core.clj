(ns metabase-enterprise.data-apps.core
  "What other modules may ask the data-apps module: which collections data apps own, what their resource files may
  hold, and the tables those read."
  (:require
   [metabase-enterprise.data-apps.db :as data-apps.db]
   [metabase-enterprise.data-apps.resource-tables :as resource-tables]
   [metabase-enterprise.data-apps.resource-validation :as resource-validation]
   [potemkin :as p]))

(p/import-vars
 [resource-tables
  record-table-dependencies!]
 [resource-validation
  problems])

(defn resource-collection?
  "Whether the collection with `collection-id` is a data app's resource collection. Remote sync syncs those with
  everything in them, as it does remote-synced collections."
  [collection-id]
  (data-apps.db/resource-collection? collection-id))

(defn resource-collection-ids
  "The IDs of every data app's resource collection."
  []
  (data-apps.db/resource-collection-ids))

(defn collection-entity-ids-present
  "Those of `entity-ids` that a collection on this instance carries."
  [entity-ids]
  (data-apps.db/collection-entity-ids-present entity-ids))

(defn delete-unowned-collections!
  "Delete the collections with `entity-ids` that no data app owns. Each entity loads in its own transaction, so a
  load that fails after creating an app's collection and before the app leaves that collection behind, where it
  would make every later pull refuse the app as naming someone else's collection."
  [entity-ids]
  (data-apps.db/delete-unowned-collections! entity-ids))

(defn unarchived-resource-collection-ids
  "The IDs of every data app's resource collection that isn't archived: the roots an export starts from."
  []
  (data-apps.db/unarchived-resource-collection-ids))
