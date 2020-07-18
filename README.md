# timestrap-android
Android app for Timestrap

## Architecture

- MVVM- Model View View Model

### View

- MainActivity
    - Fragments
        - TaskFragment
        - ProjectFragment
        - ClientFragment
        - SettingsFragment
            - LoginActivity
    - EntryActivity
    
### ViewModel

- TaskViewModel
- ProjectViewModel
- ClientViewModel
- EntryViewModel

### Model

- TimestrapDB
    - Entitites
        - Task
        - Project
        - Client
        - Entry