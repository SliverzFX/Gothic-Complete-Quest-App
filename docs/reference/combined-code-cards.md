# Combined code and item cards

The game hub now exposes Quests, Codes and Tips. Items is retained as a legacy route/enum value, redirected to the combined Codes view; original reference databases remain unchanged. Old game-hub items history reopens Codes.

CombinedToolReferences matches inventory insert/give IDs against normal item IDs, then supplements a code card with item details and all reference URLs. Name matching is only used for references without an explicit ID and an unambiguous inventory title. Character names never absorb item references. Unmatched location/stat cards remain available, classified for category filters, with no invented command.

The shared list shows only group, title, command and Copy. The entire card opens a scrollable description dialog. Copy is a separate action and does not open details. Dialogs also provide Copy, reference links, Close and standard back/outside dismissal. Tips use the same compact-card pattern. Search still searches full hidden descriptions. Source credits and version labels are preserved. No quest data, saved progress or chapter dimensions change.

Validation: merge behavior and actual Gothic 1, Gothic 2, Gothic 3 and Archolos data preserve all original descriptions, commands and source URLs. Existing instrumentation tests are updated for compact cards, full details, independent Copy and the removed Items tab. Android/Compose tests require an Android SDK/device and have not been run in this workspace.
