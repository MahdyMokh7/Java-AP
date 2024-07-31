import os
import subprocess

def find_differences(new_file_path, old_file_path, differ_file_path):
    """Compare two files line by line and save the differences to a specified file."""
    with open(new_file_path, 'r') as new_file, open(old_file_path, 'r') as old_file:
        new_lines = new_file.readlines()
        old_lines = old_file.readlines()

    differences = []
    max_lines = max(len(new_lines), len(old_lines))

    for line_number in range(max_lines):
        new_line = new_lines[line_number].strip() if line_number < len(new_lines) else ""
        old_line = old_lines[line_number].strip() if line_number < len(old_lines) else ""

        if new_line != old_line:
            differences.append(f"Line {line_number + 1}:\nOld: {old_line}\nNew: {new_line}\n")

    # Save differences to differ.txt
    with open(differ_file_path, 'w') as differ_file:
        differ_file.writelines(differences)

def clean_input_file(input_file_path):
    """Remove 'quit' and empty lines from the end of the input file."""
    with open(input_file_path, 'r') as file:
        lines = file.readlines()

    # Remove lines until a line that is neither 'quit' nor empty is found
    while lines and (lines[-1].strip() == "quit" or lines[-1] == "\n"):
        lines.pop()

    # Rewrite the cleaned lines back to the file
    with open(input_file_path, 'w') as file:
        file.writelines(lines)


if __name__ == '__main__':
    # Base directory containing the folders
    base_dir = "APS03-A4-Tests"

    # Loop through folders 1 to 10
    for i in range(1, 11):
        print(f"proccesing file {i} ...")
        folder_path = os.path.join(base_dir, str(i))
        input_file_path = os.path.join(folder_path, "input.txt")
        output_file_path = os.path.join(folder_path, "output_me.txt")
        original_output_file_path = os.path.join(folder_path, "output.txt")
        differ_file_path = os.path.join(folder_path, "differ.txt")

        # Delete previous compiled Java files in the current directory
        for file in os.listdir('.'):
            if file.endswith('.class'):
                os.remove(file)

        # Compile Java files in the current folder
        java_compile_command = ["javac", "*.java"]
        subprocess.run(java_compile_command, cwd='.')

        # Check if the input file exists
        if os.path.exists(input_file_path):
            # Clean the input file
            clean_input_file(input_file_path)

            # Append "quit" to the input file
            with open(input_file_path, 'a') as input_file:
                input_file.write("quit")

            # Prepare the command to run the Java program
            java_command = ["java", "Main", "Stages.csv", "Workers.csv"]

            # Run the Java program with input redirection and output redirection
            with open(input_file_path, 'r') as input_file, open(output_file_path, 'w') as output_file:
                subprocess.run(java_command, stdin=input_file, stdout=output_file)

            # Compare output_me.txt with output.txt and save the differences
            if os.path.exists(original_output_file_path):
                find_differences(output_file_path, original_output_file_path, differ_file_path)

    print("Processing complete.")
