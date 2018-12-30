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

type decode func(string) string

func main() {
	str := "1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011"

	//fmt.Printf("%q\n", bitsParser("1100110011001100000011000000111"))

	var output = DecodeBits(str)
	fmt.Printf("%q\n", output)


	var decoded = DecodeMorseSentance(output)
	fmt.Printf("%q\n", decoded)


}


func BreakInput(input string, matches [][]int) []string {

	var chunks []string
	var chunk = ""
	var start = 0
	var end = 0
	var previousEnd = 0
	for i := 0; i< len(matches) ; i++ {
		if previousEnd == 0 {
			start = 0
			end = matches[i][0]
			chunk = input[start:end]
			fmt.Println(chunk)
			previousEnd = matches[i][1]
			chunks = append(chunks, chunk)
		}
	}
	return chunks
}


func DecodeMorseSentance(morseCode string) string {

	morseCode = ".. --   -   .-."
	fmt.Printf(morseCode)
	wordSpaces := regexp.MustCompile("\\s{3}")
	matches := wordSpaces.FindAllStringIndex(morseCode, -1)
	words := BreakInput(morseCode, matches)
	fmt.Println(words)
	return ""
}

/*
func DecodeMorseWord(word string) string {

	//var output strings.Builder
	characterSpaces := regexp.MustCompile("\\s{1}")

	matches := characterSpaces.FindAllStringIndex(word, -1)
	fmt.Println(matches)

	return ""
}
*/


func DecodeBits(bits string) string {
	var largestSequence = maxBitsLangth(bits)
	var timeUint = largestSequence/DOT_TIMES_DASH
	var output strings.Builder

	ones := regexp.MustCompile("1+")

	matches := ones.FindAllStringIndex(bits, -1)


	var previousEnd = 0
	for _, m := range matches {

		if m[0]-previousEnd == BETWEEN_CHARACETERS * timeUint {
			output.WriteString(" ")
		} else if m[0]-previousEnd == BETWEEN_WORDS * timeUint {
			output.WriteString("   ")
		}

		if m[1]-m[0] == timeUint {
				output.WriteString(".")
		} else if m[1]-m[0] == largestSequence {
			output.WriteString("-")
		}
		previousEnd = m[1]
	}

	return output.String()
}

func maxBitsLangth(bits string) int {

	var prevLargest = 1
	for largest := 1; ; largest++ {
		ones := regexp.MustCompile("1{" + strconv.Itoa(largest) + "}")
		matches := ones.FindAllStringIndex(bits, -1)
		if len(matches) == 0 { break }
		prevLargest = largest
	}
	return prevLargest
}
