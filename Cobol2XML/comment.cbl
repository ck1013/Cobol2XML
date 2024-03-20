def convert_decimal_to_base(decimal_number, base):
    if decimal_number == 0:
        return [0]
    digits = []
    while decimal_number:
        decimal_number, remainder = divmod(decimal_number, base)
        digits.append(remainder)
    return digits[::-1]

# Example usage:
decimal_number = 10
base = 2
converted_number = convert_decimal_to_base(decimal_number, base)
print(f"The decimal number {decimal_number} in base {base} is {converted_number}.")


The decimal number 10 in base 2 is [1, 0, 1, 0].
