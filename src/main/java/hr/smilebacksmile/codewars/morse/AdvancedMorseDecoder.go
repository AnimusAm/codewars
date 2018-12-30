package main

import (
	"fmt"
	"regexp"
	//"strconv"
	"strconv"
	"strings"
)

const DOT_TIMES_DASH = 3
const BETWEEN_CHARACETERS = 3
const BETWEEN_WORDS = 7

var MORSE_CODE = map[string]string{
	"....": "H",
	".":"E",
	"-":"T",
}

func main() {
	str := "10111"

	var output = DecodeBits(str)
	fmt.Printf("%q\n", output)

	var decoded = DecodeMorse(output)
	fmt.Printf("%q\n", decoded)


}


func BreakInput(input string, splitPattern string) []string {

	var chunks []string
	var chunk = ""
	var start = 0
	var end = 0
	var previousEnd = 0
	var length = 0

	var trimmedInput = strings.TrimSpace(input)
	wordSpaces := regexp.MustCompile(splitPattern)
	matches := wordSpaces.FindAllStringIndex(trimmedInput, -1)
	length = len(matches)
	if length > 0 {
		for i := 0; i < length; i++ {
			start = previousEnd
			end = matches[i][0]
			chunk = strings.TrimSpace(trimmedInput[start:end])
			previousEnd = matches[i][1]
			chunks = append(chunks, chunk)

			if i == length-1 {
				chunk = strings.TrimSpace(trimmedInput[previousEnd:])
				chunks = append(chunks, chunk)
			}
		}
	} else {
		chunks = append(chunks, trimmedInput)
	}
	return chunks
}


func DecodeMorse(morseCode string) string {

	var output strings.Builder
	fmt.Println(morseCode)
	words := BreakInput(morseCode, "\\s{3}")
	for i := 0; i < len(words) ; i++ {
		if i > 0 {
			output.WriteString(" ")
		}
		letters := DecodeMorseWord(words[i])
		output.WriteString(letters)

	}
	return output.String()
}


func DecodeMorseWord(word string) string {

	var output strings.Builder
	letters := BreakInput(word, "\\s{1}")

	for j := 0; j < len(letters) ; j++ {

		if decoded, exists := MORSE_CODE[letters[j]]; exists {
			output.WriteString(decoded)
		}
	}
	return output.String()
}


func DecodeBits(bits string) string {
	fmt.Println(bits)

	var largestSequence = maxBitsLength(bits)
	var timeUint = largestSequence/DOT_TIMES_DASH
	var output strings.Builder

	ones := regexp.MustCompile("1+")

	matches := ones.FindAllStringIndex(bits, -1)

	var all = 0
	if len(matches) > 1 {
		for _, m := range matches {
			if (m[1]-m[0])/DOT_TIMES_DASH == 0 {
				all = all+1
			}
		}
	}

	if len(matches) == all || len(matches) == 1 {
		timeUint = largestSequence
	}

	var previousEnd= 0
	for _, m := range matches {

		if m[0]-previousEnd == BETWEEN_CHARACETERS*timeUint {
			output.WriteString(" ")
		} else if m[0]-previousEnd == BETWEEN_WORDS*timeUint {
			output.WriteString("   ")
		}

		if m[1]-m[0] <= timeUint {
			output.WriteString(".")
		} else if m[1]-m[0] >= largestSequence {
			output.WriteString("-")
		}
		previousEnd = m[1]
	}


	return output.String()
}


func maxBitsLength(bits string) int {

	var prevLargest = 1
	for largest := 1; ; largest++ {
		ones := regexp.MustCompile("1{" + strconv.Itoa(largest) + "}")
		matches := ones.FindAllStringIndex(bits, -1)
		if len(matches) == 0 { break }
		prevLargest = largest
	}
	return prevLargest
}

func minBitsLength(bits string) int {

	var prevLowest = 0
	for lowest := 1; ; lowest++ {
		ones := regexp.MustCompile("1{" + strconv.Itoa(1) + "}")
		matches := ones.FindAllStringIndex(bits, -1)
		if len(matches) > 0 { break }
		prevLowest = lowest
	}
	return prevLowest
}

func maxZerosLength(bits string) int {

	var prevLargest = 1
	for largest := 1; ; largest++ {
		ones := regexp.MustCompile("0{" + strconv.Itoa(largest) + "}")
		matches := ones.FindAllStringIndex(bits, -1)
		if len(matches) == 0 { break }
		prevLargest = largest
	}
	return prevLargest
}
