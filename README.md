# Dice Roller — Рандомайзер "Бросок кубика"

## Вариант 5

Мобильное Android-приложение для генерации случайных бросков игральных костей различных типов.

## Описание

Приложение позволяет выбрать тип кубика (d4, d6, d8, d10, d12, d20) и количество кубиков (от 1 до 5), выполнить бросок и увидеть результат. Все броски сохраняются в истории и доступны для просмотра.

## Реализованные функции

- Выбор типа кубика: d4, d6, d8, d10, d12, d20
- Выбор количества кубиков: от 1 до 5
- Генерация случайного броска с отображением результатов каждого кубика и общей суммы
- Сохранение истории бросков в локальной базе данных (Room)
- Просмотр истории бросков с датой и временем
- Очистка истории бросков с подтверждением
- Обработка некорректного пользовательского ввода
- Навигация между экранами через BottomNavigationView

## Технологии

- Kotlin
- Android Studio
- MVVM (ViewModel + LiveData)
- Room Database
- Navigation Component
- RecyclerView + DiffUtil
- View Binding
- Material Design 3

## Структура 

```
app/src/main/java/com/zavetg/diceroller/
├── MainActivity.kt              — главная Activity с навигацией
├── DiceRollerApplication.kt     — Application-класс (инициализация БД)
├── data/
│   ├── DiceRoll.kt              — Room Entity (модель броска)
│   ├── DiceRollDao.kt           — Data Access Object (запросы к БД)
│   ├── AppDatabase.kt           — Room Database (singleton)
│   └── DiceRollRepository.kt    — Repository (абстракция данных)
└── ui/
    ├── RollFragment.kt          — экран броска кубика
    ├── HistoryFragment.kt       — экран истории бросков
    ├── DiceViewModel.kt         — ViewModel + ViewModelFactory
    └── DiceRollAdapter.kt       — RecyclerView Adapter
```