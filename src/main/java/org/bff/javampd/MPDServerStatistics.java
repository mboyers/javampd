package org.bff.javampd;

import com.google.inject.Inject;
import org.bff.javampd.exception.MPDResponseException;
import org.bff.javampd.properties.ServerProperties;

import java.util.List;

/**
 * @author bill
 */
public class MPDServerStatistics implements ServerStatistics {

    @Inject
    private ServerProperties serverProperties;
    @Inject
    private CommandExecutor commandExecutor;

    /**
     * Returns the statistics of the requested statistics element.
     * See <code>StatList</code> for a list of possible items returned
     * by getServerStat.
     *
     * @param stat the statistic desired
     * @return the requested statistic
     * @throws MPDResponseException if the MPD response generates an error
     */
    private String getStat(StatList stat) throws MPDResponseException {
        List<String> respList = sendMPDCommand(serverProperties.getStats());

        for (String line : respList) {
            if (line.startsWith(stat.getStatPrefix())) {
                return line.substring(stat.getStatPrefix().length()).trim();
            }
        }
        return null;
    }

    private List<String> sendMPDCommand(String stats) throws MPDResponseException {
        return commandExecutor.sendCommand(stats);
    }

    @Override
    public long getPlaytime() throws MPDResponseException {
        return Long.parseLong(getStat(StatList.PLAYTIME));
    }

    @Override
    public long getUptime() throws MPDResponseException {
        return Long.parseLong(getStat(StatList.UPTIME));
    }

    @Override
    public int getAlbums() throws MPDResponseException {
        return Integer.parseInt(getStat(StatList.ALBUMS));
    }

    @Override
    public int getArtists() throws MPDResponseException {
        return Integer.parseInt(getStat(StatList.ARTISTS));
    }

    @Override
    public int getSongs() throws MPDResponseException {
        return Integer.parseInt(getStat(StatList.SONGS));
    }

    @Override
    public long getDatabasePlaytime() throws MPDResponseException {
        return Integer.parseInt(getStat(StatList.DBPLAYTIME));
    }

    @Override
    public long getDatabaseUpdateTime() throws MPDResponseException {
        return Long.parseLong(getStat(StatList.DBUPDATE));
    }
}
