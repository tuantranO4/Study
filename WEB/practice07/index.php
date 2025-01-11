<?php
// declare(strict_types=1);

$numbers = [1, 2, 3, 4, 5, 6, 7, 8];
$even_numbers = [2, 4, 6, 8];

$errors = ["Syntax Error", "Stack Overflow", "Out of Memory"];

$bank = [
    [
        "question" => " What is it good for?",
        "answers" => [
            "a" => "To make Love, not war",
            "b" => "Absolutely nothing",
            "c" => "Give you my heart",
            "d" => "Just because"
        ],
        "correct_answer" => "b"
    ],
    [
        "question" => " Are happy, Vincent?",
        "answers" => [
            1 => "Tell me again, you MTF",
            2 => "Yes, we are happy",
            3 => "Cheeseburger"
        ],
        "correct_answer" => 2
    ]
];

function factorial(int $n)
{
    if ($n == 0) return 1;

    return $n * factorial($n - 1);
}

function test_even(int $n): bool
{
    return !($n % 2);
}

function array_every_own(array $arr, callable $fn): bool
{
    foreach ($arr as $e)
        if (!$fn($e))
            return false;
    return true;
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        input:checked + label {
            font-weight: bold;
            color: green;
        }
    </style>
</head>

<body>
    <h3>Task 1. - Factorial with recursive function</h3>
    <?= factorial(5); ?>

    <h3>Task 2. - "Hello World" in all sizes</h3>
    <?php
    for ($i = 1; $i <= 6; $i++) {
        echo "<h{$i}>Hello World!</h{$i}>";
    }
    ?>

    <h3>Task 3. - Array Functions</h3>
    <?php
    // echo implode(" , ",$numbers)."<br>";
    print_r($numbers);
    echo "<br><br>";
    // var_dump($numbers);
    print_r(array_filter($numbers, function ($x) {
        return $x % 2 == 0;
    }));
    echo "<br><br>";
    print_r(array_map(fn($x) => $x * $x, array_filter($numbers, fn($x) => $x % 2 == 0)));

    ?>
    <h3>Task 4. - Iterate arrays</h3>
    <?php
    var_dump(array_every_own($numbers, "test_even"));
    echo "<br><br>";
    var_dump(array_every_own($even_numbers, "test_even"));
    echo "<br><br>";
    echo array_every_own($numbers, "test_even");
    echo "<br><br>";
    echo array_every_own($even_numbers, "test_even");
    ?>
    <h3>Task 5. - shorten form of cycles</h3>
    <ul>
        <?php foreach ($errors as $e): ?>
            <li>
                <?= $e ?>
            </li>
        <?php endforeach ?>
    </ul>

    <?php foreach ($bank as $id => $q): ?>
        <b><?= strval($id + 1) . ". " . $q["question"] ?></b><br>
        <?php foreach ($q["answers"] as $sign => $text): ?>
            <input type="radio" id="q<?= $id."-".$sign?>" name="q<?= $id ?>" value="<? $sign ?>" <?= $sign == $q["correct_answer"] ? "checked" : "" ?> disabled> <label for="q<?= $id."-".$sign?>"><?= $sign . ".) " . $text ?></label><br>
        <?php endforeach ?><br>
    <?php endforeach ?>
</body>

</html>