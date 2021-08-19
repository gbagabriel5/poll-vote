package com.gba.pollvote.config;

import lombok.Getter;

@Getter
public class ErrorsInfo {
    public static final String POLL_EXIST = "Pauta ja cadastrada!";
    public static final String POLL_NOT_FOUND = "Pauta nao encontrada!";
    public static final String SESSION_NOT_FOUND = "Sessao nao encontrada!";
    public static final String NAME_FOUND = "Nome ja cadastrado!";
    public static final String NULL_NAME = "Preencha o campo nome!";
    public static final String CPF_FOUND = "CPF ja cadastrado!";
    public static final String CPF_NOT_FOUND = "CPF não encontrado!";
    public static final String INVALID_CPF = "CPF Invalido!";
    public static final String NULL_CPF = "Preencha o campo cpf!";
    public static final String UNABLE_TO_VOTE = "Usuario incapaz de votar!";
    public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    public static final String SESSION_ENDS = "Sessao encerrada!";
    public static final String ASSOCIATE_VOTE_EXISTS = "Este Associado ja votou.";
    public static final String ASSOCIATE_NOT_FOUND = "Associado nao encontrado!";
    public static final String EXCEPTION_ERROR = "Error Exception: ";
    public static final String NULL_PARAMETERS = "Informe um dos parametros de busca!";
}